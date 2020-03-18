package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


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
        userRepository.insertUsername(username, newUser.getId());
        userRepository.insertHandle(handle, newUser.getId());
        userRepository.insertEmail(email, newUser.getId());
        userRepository.getIncomingFriendRequestMap().put(newUser.getId(), new ConcurrentHashMap<>());
        userRepository.getOutgoingFriendRequestMap().put(newUser.getId(), new ConcurrentHashMap<>());
        userRepository.getFriendMap().put(newUser.getId(), new ConcurrentHashMap<>());
        userRepository.getPaymentListMap().put(newUser.getId(), new ArrayList<>());
        userRepository.getCashListMap().put(newUser.getId(), new ArrayList<>());
        userRepository.getBankAccountListMap().put(newUser.getId(), new ArrayList<>());
        userRepository.save(newUser);
        return newUser;
    }

    public ArrayList<Integer> check_if_taken(String username, String email, String handle) {
        ArrayList<Integer> errors = new ArrayList<>();

        if (userRepository.containsUsername(username))
            errors.add(-2);
        if (userRepository.containsEmail(email))
            errors.add(-3);
        if (userRepository.containsHandle(handle))
            errors.add(-4);
        return errors;

    }

    private boolean editUsername(String id, String newUsername) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return false;
        if (userRepository.changeUsername(user.get().getUsername(), newUsername, id)) {
            user.get().setUsername(newUsername);
            return true;
        } else
            return false;
    }

    private boolean editEmail(String id, String newEmail) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return false;
        if (userRepository.changeEmail(user.get().getEmail(), newEmail, id)) {
            user.get().setEmail(newEmail);
            return true;
        } else
            return false;
    }

    private boolean editHandle(String id, String newHandle) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return false;
        if (userRepository.changeHandle(user.get().getHandle(), newHandle, id)) {
            user.get().setHandle(newHandle);
            return true;
        } else
            return false;
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
        }
        return errors;
    }

    // This is also used to decline Friend Requests
    // -1 is user not found
    // -2 if friend not found in incoming friend request
    // 0 if success
    public int cancelOutgoingFriendRequest(String userId, String friendId) {
        if (!userRepository.existsById(userId) || !userRepository.existsById(friendId)) {
            return -1;
        } else {
            //if compare to returns pos use that user first
            //else use other
            synchronized (userRepository.getFriendMap()) {
                synchronized (userRepository.getOutgoingFriendRequestMap()) {
                    synchronized (userRepository.getIncomingFriendRequestMap()) {
                        deleteOutgoingFriendRequest(userId, friendId);
                        deleteIncomingFriendRequest(friendId, userId);
                    }
                }
            }
            return 0;
        }
    }

    // -1 if user not found
    public int acceptIncomingRequest(String userId, String friendId) {
        if (!userRepository.existsById(userId) || !userRepository.existsById(friendId)) {
            return -1;
        } else {
            synchronized (userRepository.getFriendMap()) {
                synchronized (userRepository.getOutgoingFriendRequestMap()) {
                    synchronized (userRepository.getIncomingFriendRequestMap()) {
                        if (!acceptIncomingFriendRequest(userId, friendId)) return -2;
                        acceptedOutgoingFriendRequest(friendId, userId);
                    }
                }
            }
            return 0;
        }
    }

    // -1 if users not found
    // -2 if friend request already sent
    public int sendOutRequest(String userId, String friendId) {
        if (!userRepository.existsById(userId) || !userRepository.existsById(friendId)) {
            return -1;
        } else {
            ConcurrentHashMap<String, Boolean> userOutgoingFriendRequests = getUserOutgoingFriendRequest(userId);
            if (userOutgoingFriendRequests.containsKey(friendId)) return -2;
            synchronized (userRepository.getFriendMap()) {
                synchronized (userRepository.getOutgoingFriendRequestMap()) {
                    synchronized (userRepository.getIncomingFriendRequestMap()) {
                        sendOutgoingFriendRequest(userId, friendId);
                        receivedIncomingFriendRequest(friendId, userId);
                    }
                }
            }
            return 0;
        }
    }

    public User getUserWithUsername(String username, String password) {
        String userId = userRepository.getIdfromUsername(username);
        if (userId == null) return null;

        Optional<User> curUser = userRepository.findById(userId);
        if (curUser.isEmpty()) return null;
        if (curUser.get().getPassword().equals(password)) {
            return curUser.get();
        } else {
            return null;
        }
    }

    public void addPayment(String userId, String paymentId) {
        userRepository.getPaymentListMap().get(userId).add(paymentId);
    }

    private void addFriend(String userId, String friendId) {
        userRepository.getFriendMap().get(userId).put(friendId, true);
    }

    public boolean removeFriend(String userId, String friendId) {
        return userRepository.getFriendMap().get(userId).remove(friendId) != null;
    }

    public boolean deleteIncomingFriendRequest(String userId, String friendId) {
        return userRepository.getIncomingFriendRequestMap().get(userId).remove(friendId) != null;
    }

    public boolean deleteOutgoingFriendRequest(String userId, String friendId) {
        return userRepository.getOutgoingFriendRequestMap().get(userId).remove(friendId) != null;
    }

    public boolean acceptIncomingFriendRequest(String userId, String friendId) {
        if (deleteIncomingFriendRequest(userId, friendId)) {
            userRepository.getFriendMap().get(userId).put(friendId, true);
            return true;
        }
        return false;
    }

    // Adds incoming friend request. Returns true if accepted or put into incoming Friend Request Map
    public boolean receivedIncomingFriendRequest(String userId, String friendId) {
        // already sent a request so just add friend
        if (userRepository.getFriendMap().get(userId).containsKey(friendId))
            return false;
        else if (userRepository.getOutgoingFriendRequestMap().get(userId).containsKey(friendId)) {
            acceptedOutgoingFriendRequest(userId, friendId);
            return true;
        } else {
            userRepository.getIncomingFriendRequestMap().get(userId).put(friendId, true);
            return true;
        }
    }

    public void acceptedOutgoingFriendRequest(String userId, String friendId) {
        if (userRepository.getOutgoingFriendRequestMap().get(userId).remove(friendId) != null) {
            addFriend(userId, friendId);
        }
    }

    // Sends friend request. Returns true if sent or accepted. Returns false if already friends
    public boolean sendOutgoingFriendRequest(String userId, String friendId) {
        if (userRepository.getFriendMap().get(userId).containsKey(friendId)) {
            return false;
        } else if (userRepository.getIncomingFriendRequestMap().get(userId).containsKey(friendId)) {
            acceptIncomingFriendRequest(userId, friendId);
            return true;
        } else {
            userRepository.getOutgoingFriendRequestMap().get(userId).put(friendId, true);
            return true;
        }
    }

    public void addCash(String userId, String cashId) {
        userRepository.getCashListMap().get(userId).add(cashId);
    }

    public boolean checkBankAccount(String userId, String bankAccountId) {
        return userRepository.getBankAccountListMap().get(userId).contains(bankAccountId);
    }

    public void addBankAccount(String userId, String bankAccountId) {
        userRepository.getBankAccountListMap().get(userId).add(bankAccountId);
    }

    public ConcurrentHashMap<String, Boolean> getUserFriendList(String userId) {
        return userRepository.getFriendMap().get(userId);
    }

    public ConcurrentHashMap<String, Boolean> getUserIncomingFriendRequest(String userId) {
        return userRepository.getIncomingFriendRequestMap().get(userId);
    }

    public ConcurrentHashMap<String, Boolean> getUserOutgoingFriendRequest(String userId) {
        return userRepository.getOutgoingFriendRequestMap().get(userId);
    }

    public int sendOutRequestWithUsername(String username, String friendUsername) {
        String userId = userRepository.getIdfromUsername(username);
        String friendId = userRepository.getIdfromUsername(friendUsername);
        if (userId == null || friendId == null) {
            return -1;
        }
        return sendOutRequest(userId, friendId);
    }

    public ArrayList<String> getUserBankAccountList(String userId) {
        return userRepository.getBankAccountListMap().get(userId);
    }

    public Long getUserBalance(String userId) {
        Optional<User> curUser = userRepository.findById(userId);
        return curUser.map(User::getBalance).orElse(null);
    }

    // -1 is if either user not found
    // -2 if not friends
    public int deleteFriend(String userId, String friendId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<User> friendUser = userRepository.findById(friendId);
        if (user.isEmpty() || friendUser.isEmpty()) return -1;
        else
            return userRepository.deleteFriends(userId, friendId);
    }
}
