package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler;
import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.handler.UserController;
import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User addUser(User user) throws InValidFieldValueException {
        check_if_taken(user.getUsername(), user.getEmail(), user.getHandle());
        userRepository.save(user);
        return user;
    }

    public void check_if_taken(String username, String email, String handle) throws InValidFieldValueException {
        if (userRepository.containsUsername(username))
            throw new InValidFieldValueException("Username");
        if (userRepository.containsEmail(email))
            throw new InValidFieldValueException("Email");
        if (userRepository.containsHandle(handle))
            throw new InValidFieldValueException("Handle");
    }

    @Transactional
    public boolean editUsername(String userId, String newUsername) throws InValidFieldValueException {
        User curUser = checkValidUserId(userId);
        if (checkChangeUsername(newUsername)) {
            curUser.setUsername(newUsername);
            userRepository.save(curUser);
            return true;
        } else return false;
    }

    @Transactional
    public boolean editEmail(String userId, String newEmail) throws InValidFieldValueException {
        User curUser = checkValidUserId(userId);
        if (checkChangeEmail(newEmail)) {
            curUser.setEmail(newEmail);
            userRepository.save(curUser);
            return true;
        } else return false;
    }

    @Transactional
    public boolean editHandle(String userId, String newHandle) throws InValidFieldValueException {
        User curUser = checkValidUserId(userId);
        if (checkChangeHandle(newHandle)) {
            curUser.setHandle(newHandle);
            userRepository.save(curUser);
            return true;
        } else return false;
    }

    // empty if no errors
    // -1 is no user with id exists
    // -2 is Username exists already
    // -3 Email already exists
    // -4 Handle already exists
    public ArrayList<Integer> editProfile(String userId, String newName, String newUsername, String newPassword,
                                          String newEmail, String newHandle) {
        ArrayList<Integer> errors = new ArrayList<>();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            errors.add(-1);
        } else {
            user.get().setName(newName);
            user.get().setPassword(newPassword);
            userRepository.save(user.get());
            try {
                if (!editUsername(userId, newUsername))
                    errors.add(-2);
            } catch (Exception e) {
                errors.add(-2);
            }
            try {
                if (!editEmail(userId, newEmail))
                    errors.add(-3);
            } catch (Exception e) {
                errors.add(-3);
            }
            try {
                if (!editHandle(userId, newHandle))
                    errors.add(-4);
            } catch (Exception e) {
                errors.add(-4);
            }
        }
        return errors;
    }

    @Transactional
    public int cancelOutgoingFriendRequest(String userId, String friendId) throws InValidFieldValueException {
        User user = checkValidUserId(userId);
        User friend = checkValidUserId(friendId, "Friend Id");
        user.removeOutgoingFriendRequest(friend);
        friend.removeIncomingFriendRequest(user);
        userRepository.save(user);
        userRepository.save(friend);
        return 0;
    }

    @Transactional
    public int acceptIncomingRequest(String userId, String friendId) throws InValidFieldValueException, BaseExceptionHandler.FriendRequestDoesNotExistException {
        User user = checkValidUserId(userId);
        User friend = checkValidUserId(friendId, "Friend Id");
        acceptIncomingFriendRequest(user, friend);
        acceptedOutgoingFriendRequest(friend, user);
        userRepository.save(user);
        userRepository.save(friend);
        return 0;
    }

    @Transactional
    public int sendOutRequest(String userId, String friendId) throws InValidFieldValueException, BaseExceptionHandler.FriendRequestDoesNotExistException, BaseExceptionHandler.FriendRequestAlreadyExistException {
        User user = checkValidUserId(userId);
        User friend = checkValidUserId(friendId, "Friend Id");
        if (user.isOutgoingFriend(friend) || user.isFriend(friend))
            throw new BaseExceptionHandler.FriendRequestAlreadyExistException("from " + userId + " to " + friendId);
        sendOutgoingFriendRequest(user, friend);
        receivedIncomingFriendRequest(friend, user);
        userRepository.save(user);
        userRepository.save(friend);
        return 0;
    }

    public User getUserWithUsername(String username, String password) throws InValidFieldValueException {
        String userId = userRepository.getIdfromUsername(username);
        if (userId == null) throw new InValidFieldValueException("Invalid Username");

        User curUser = checkValidUserId(userId);
        if (!curUser.getPassword().equals(password)) {
            throw new InValidFieldValueException("Invalid Password");
        }
        return curUser;
    }

    @Transactional
    public boolean acceptIncomingFriendRequest(User user, User friend) throws BaseExceptionHandler.FriendRequestDoesNotExistException {
        if (user.removeIncomingFriendRequest(friend)) {
            addFriend(user, friend);
            return true;
        }
        throw new BaseExceptionHandler.FriendRequestDoesNotExistException(user.getId() + " " + friend.getId());
    }

    @Transactional
    public void receivedIncomingFriendRequest(User user, User friend) {
        // already sent a request so just add friend
        if (user.isFriend(friend)) {
        } else if (user.isOutgoingFriend(friend)) {
            acceptedOutgoingFriendRequest(user, friend);
        } else {
            user.addIncomingFriendRequest(friend);
        }
    }

    @Transactional
    public void acceptedOutgoingFriendRequest(User user, User friend) {
        if (user.removeOutgoingFriendRequest(friend)) {
            addFriend(user, friend);
        }
    }

    @Transactional
    public void sendOutgoingFriendRequest(User user, User friend) throws BaseExceptionHandler.FriendRequestDoesNotExistException {
        if (user.isFriend(friend)) {
        } else if (user.isIncomingFriend(friend)) {
            acceptIncomingFriendRequest(user, friend);
        } else {
            user.addOutgoingFriendRequest(friend);
        }
    }

    public Long getUserBalance(String userId) throws InValidFieldValueException {
        User user = checkValidUserId(userId);
        return user.getBalance();
    }

    public Set<User> getUserFriendSet(String userId) throws InValidFieldValueException {
        User user = checkValidUserId(userId);
        return user.getFriendSet();
    }

    public Set<User> getOutgoingFriendRequestSet(String userId) throws InValidFieldValueException {
        User user = checkValidUserId(userId);
        return user.getOutgoingFriendRequestSet();
    }

    public Set<User> getIncomingFriendRequestSet(String userId) throws InValidFieldValueException {
        User user = checkValidUserId(userId);
        return user.getIncomingFriendRequestSet();
    }

    public Set<BankAccount> getBankAccountSet(String userId) throws InValidFieldValueException {
        User user = checkValidUserId(userId);
        return user.getBankAccountSet();
    }

    // -1 is if either user not found
    // -2 if not friends
    @Transactional
    public int deleteFriend(String userId, String friendId) throws InValidFieldValueException {
        User user = checkValidUserId(userId);
        User friend = checkValidUserId(friendId, "Friend Id");
        user.deleteFriend(friend);
        friend.deleteFriend(user);
        userRepository.save(user);
        userRepository.save(friend);
        return 0;
    }

    private boolean checkChangeEmail(String newEmail) {
        return !userRepository.containsEmail(newEmail);
    }

    private boolean checkChangeUsername(String newUsername) {
        return !userRepository.containsUsername(newUsername);
    }

    private boolean checkChangeHandle(String newHandle) {
        return !userRepository.containsHandle(newHandle);
    }

    @Transactional
    public void addFriend(User user, User friend) {
        user.addFriend(friend);
    }


    public Set<User> findUsers(UserController.FindUsersRequest findUsersRequest) {
        if (findUsersRequest.isHandle())
            return userRepository.findByHandleStartsWith(findUsersRequest.getMatch());
        else if (findUsersRequest.isUsername())
            return userRepository.findByUsernameStartsWith(findUsersRequest.getMatch());
        else if (findUsersRequest.isEmail())
            return userRepository.findByEmailStartsWith(findUsersRequest.getMatch());
        return null;
    }

    public User checkValidUserId(String userId) throws InValidFieldValueException {
        Optional<User> curUser = userRepository.findById(userId);
        if (curUser.isEmpty())
            throw new InValidFieldValueException("Invalid User Id");
        return curUser.get();
    }

    public User checkValidUserId(String userId, String msg) throws InValidFieldValueException {
        Optional<User> curUser = userRepository.findById(userId);
        if (curUser.isEmpty())
            throw new InValidFieldValueException("Invalid " + msg);
        return curUser.get();
    }
}
