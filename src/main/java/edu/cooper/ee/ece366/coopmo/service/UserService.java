package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.handler.BaseExceptionHandler.InValidFieldValueException;
import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User createUser(String name, String username, String password, String email, String handle) {
        User newUser = new User(name, username, password, email, handle);
        if (userRepository.containsUsername(username) || userRepository.containsEmail(email) || userRepository.containsHandle(handle)) {
            return null;
        }
        userRepository.save(newUser);
        return newUser;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void check_if_taken(String username, String email, String handle) throws InValidFieldValueException {
        if (userRepository.containsUsername(username))
            throw new InValidFieldValueException("Invalid Username");
        if (userRepository.containsEmail(email))
            throw new InValidFieldValueException("Invalid Email");
        if (userRepository.containsHandle(handle))
            throw new InValidFieldValueException("Invalid handle");
    }

    private boolean editUsername(String id, String newUsername) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty() || user.get().getUsername().equals(newUsername)) return false;
        if (checkChangeUsername(newUsername)) {
            user.get().setUsername(newUsername);
            userRepository.save(user.get());
            return true;
        } else return false;
    }

    private boolean editEmail(String id, String newEmail) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty() || user.get().getEmail().equals(newEmail)) return false;
        if (checkChangeEmail(newEmail)) {
            user.get().setEmail(newEmail);
            userRepository.save(user.get());
            return true;
        } else return false;
    }

    private boolean editHandle(String id, String newHandle) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty() || user.get().getHandle().equals(newHandle)) return false;
        if (checkChangeHandler(newHandle)) {
            user.get().setEmail(newHandle);
            userRepository.save(user.get());
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
            if (!editUsername(userId, newUsername))
                errors.add(-2);
            if (!editEmail(userId, newEmail))
                errors.add(-3);
            if (!editHandle(userId, newHandle))
                errors.add(-4);
            userRepository.save(user.get());
        }
        return errors;
    }

    // This is also used to decline Friend Requests
    // -1 is user not found
    // -2 if friend not found in incoming friend request
    // 0 if success
    public int cancelOutgoingFriendRequest(String userId, String friendId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<User> friend = userRepository.findById(friendId);
        if (user.isEmpty() || friend.isEmpty()) return -1;
        user.get().removeOutgoingFriendRequest(friend.get());
        friend.get().removeIncomingFriendRequest(user.get());
        userRepository.save(user.get());
        userRepository.save(friend.get());
        return 0;
    }

    // -1 if user not found
    public int acceptIncomingRequest(String userId, String friendId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<User> friend = userRepository.findById(friendId);
        if (user.isEmpty() || friend.isEmpty()) return -1;
        if (!acceptIncomingFriendRequest(user.get(), friend.get())) return -2;
        acceptedOutgoingFriendRequest(friend.get(), user.get());
        userRepository.save(user.get());
        userRepository.save(friend.get());
        return 0;
    }

    // -1 if users not found
    // -2 if friend request already sent
    public int sendOutRequest(String userId, String friendId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<User> friend = userRepository.findById(friendId);
        if (user.isEmpty() || friend.isEmpty()) return -1;
        if (user.get().isOutgoingFriend(friend.get()) || user.get().isFriend(friend.get())) return -2;
        sendOutgoingFriendRequest(user.get(), friend.get());
        receivedIncomingFriendRequest(friend.get(), user.get());
        userRepository.save(user.get());
        userRepository.save(friend.get());
        return 0;
    }

    public User getUserWithUsername(String username, String password) throws InValidFieldValueException {
        String userId = userRepository.getIdfromUsername(username);
        if (userId == null) throw new InValidFieldValueException("Invalid Username");

        Optional<User> curUser = userRepository.findById(userId);
        if (curUser.isEmpty()) throw new InValidFieldValueException("Invalid Username");
        if (!curUser.get().getPassword().equals(password)) {
            throw new InValidFieldValueException("Invalid Password");
        }
        return curUser.get();
    }

    public boolean acceptIncomingFriendRequest(User user, User friend) {
        if (user.removeIncomingFriendRequest(friend)) {
            addFriend(user, friend);
            return true;
        }
        return false;
    }

    // Adds incoming friend request. Returns true if accepted or put into incoming Friend Request Map
    public boolean receivedIncomingFriendRequest(User user, User friend) {
        // already sent a request so just add friend
        if (user.isFriend(friend))
            return false;
        else if (user.isOutgoingFriend(friend)) {
            acceptedOutgoingFriendRequest(user, friend);
            return true;
        } else {
            user.addIncomingFriendRequest(friend);
            return true;
        }
    }

    public void acceptedOutgoingFriendRequest(User user, User friend) {
        if (user.removeOutgoingFriendRequest(friend)) {
            addFriend(user, friend);
        }
    }

    // Sends friend request. Returns true if sent or accepted. Returns false if already friends
    public boolean sendOutgoingFriendRequest(User user, User friend) {
        if (user.isFriend(friend)) {
            return false;
        } else if (user.isIncomingFriend(friend)) {
            acceptIncomingFriendRequest(user, friend);
            return true;
        } else {
            user.addOutgoingFriendRequest(friend);
            return true;
        }
    }

    public int sendOutRequestWithUsername(String username, String friendUsername) {
        String userId = userRepository.getIdfromUsername(username);
        String friendId = userRepository.getIdfromUsername(friendUsername);
        if (userId == null || friendId == null) {
            return -1;
        }
        return sendOutRequest(userId, friendId);
    }

    public Long getUserBalance(String userId) {
        Optional<User> curUser = userRepository.findById(userId);
        return curUser.map(User::getBalance).orElse(null);
    }

    public Set<User> getUserFriendList(String userId) {
        Optional<User> curUser = userRepository.findById(userId);
        return curUser.map(User::getFriendSet).orElse(null);
    }

    public Set<User> getOutgoingFriendRequest(String userId) {
        Optional<User> curUser = userRepository.findById(userId);
        return curUser.map(User::getOutgoingFriendRequestSet).orElse(null);
    }

    public Set<User> getIncomingFriendRequest(String userId) {
        Optional<User> curUser = userRepository.findById(userId);
        return curUser.map(User::getIncomingFriendRequestSet).orElse(null);
    }

    public Set<BankAccount> getBankAccountList(String userId) {
        Optional<User> curUser = userRepository.findById(userId);
        return curUser.map(User::getBankAccount).orElse(null);
    }

    // -1 is if either user not found
    // -2 if not friends
    public int deleteFriend(String userId, String friendId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<User> friend = userRepository.findById(friendId);
        if (user.isEmpty() || friend.isEmpty()) return -1;
        user.get().deleteFriend(friend.get());
        friend.get().deleteFriend(user.get());
        userRepository.save(user.get());
        userRepository.save(friend.get());
        return 0;
    }

    private boolean checkChangeEmail(String newEmail) {
        return !userRepository.containsEmail(newEmail);
    }

    private boolean checkChangeUsername(String newUsername) {
        return !userRepository.containsUsername(newUsername);
    }

    private boolean checkChangeHandler(String newHandle) {
        return !userRepository.containsHandle(newHandle);
    }

    private void addFriend(User user, User friend) {
        user.addFriend(friend);
    }
}
