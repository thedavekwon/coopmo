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
        if (userRepository.containsUsername(username) || userRepository.containsUsername(username) || userRepository.containsUsername(username)) {
            return null;
        }
        userRepository.insertUsername(username, newUser.getId());
        userRepository.insertHandle(username, newUser.getId());
        userRepository.insertEmail(username, newUser.getId());
        userRepository.getIncomingFriendRequestMap().put(newUser.getId(), new ConcurrentHashMap<>());
        userRepository.getOutgoingFriendRequestMap().put(newUser.getId(), new ConcurrentHashMap<>());
        userRepository.getFriendMap().put(newUser.getId(), new ConcurrentHashMap<>());
        userRepository.getPaymentList().put(newUser.getId(), new ArrayList<>());
        userRepository.getCashList().put(newUser.getId(), new ArrayList<>());
        userRepository.getBankAccountList().put(newUser.getId(), new ArrayList<>());

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
        if (!user.isPresent()) return false;
        if (userRepository.changeUsername(user.get().getUsername(), newUsername
                , id)) {
            user.get().setUsername(newUsername);
            return true;
        } else
            return false;
    }

    private boolean editEmail(String id, String newEmail) {
        Optional<User> user = userRepository.findById(id);
        return user.filter(value -> userRepository.changeEmail(value.getEmail(), newEmail, id)).isPresent();
    }

    private boolean editHandle(String id, String newHandle) {
        Optional<User> user = userRepository.findById(id);
        return user.filter(value -> userRepository.changeHandle(value.getHandle(), newHandle, id)).isPresent();
    }

    // empty if no errors
    // -1 is no user with id exists
    // -2 is Username exists already
    // -3 Email already exists
    // -4 Handle already exists
    public ArrayList<Integer> editProfile(String id, String newName, String newUsername, String newPassword,
                                          String newEmail, String newHandle) {
        ArrayList<Integer> errors = new ArrayList<>();
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            errors.add(-1);
        } else {
            user.get().setName(newName);
            user.get().setPassword(newPassword);
            if (editUsername(id, newUsername))
                errors.add(-2);
            if (editEmail(id, newEmail))
                errors.add(-3);
            if (editHandle(id, newHandle))
                errors.add(-4);
        }
        return errors;
    }

    // This is also used to decline Friend Requests
    // -1 is user not found
    // -2 if friend not found in incoming friend request
    // 0 if success
    public int cancelOutgoingFriendRequest(String userId, String friendId) {
        Optional<User> priUser = userRepository.findById(userId);
        Optional<User> secUser = userRepository.findById(friendId);

        if (!priUser.isPresent() || !secUser.isPresent()) {
            return -1;
        } else {
            //if compare to returns pos use that user first
            //else use other
            if (userId.compareTo(friendId) > 0) {
                synchronized (priUser.get()) {
                    synchronized (secUser.get()) {
                        deleteOutgoingFriendRequest(userId, friendId);
                        deleteIncomingFriendRequest(friendId, userId);
                    }
                }
            } else {
                synchronized (secUser.get()) {
                    synchronized (priUser.get()) {
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
        Optional<User> priUser = userRepository.findById(userId);
        Optional<User> secUser = userRepository.findById(friendId);

        if (!priUser.isPresent() || !secUser.isPresent()) {
            return -1;
        } else {
            //if compare to returns pos use that user first
            //else use other
            if (userId.compareTo(friendId) > 0) {
                synchronized (priUser.get()) {
                    synchronized (secUser.get()) {
                        if (!acceptIncomingFriendRequest(userId, friendId))
                            return -2;
                        acceptedOutgoingFriendRequest(friendId, userId);
                    }
                }
            } else {
                synchronized (secUser.get()) {
                    synchronized (priUser.get()) {
                        if (!acceptIncomingFriendRequest(userId, friendId))
                            return -2;
                        acceptedOutgoingFriendRequest(friendId, userId);
                    }
                }
            }
            return 0;
        }
    }

    // -1 if users not found
    // -2 if already friends
    public int sendOutRequest(String userId, String friendId) {
        Optional<User> priUser = userRepository.findById(userId);
        Optional<User> secUser = userRepository.findById(friendId);

        if (!priUser.isPresent() || !secUser.isPresent()) {
            return -1;
        } else {
            //if compare to returns pos use that user first
            //else use other
            if (userId.compareTo(friendId) > 0) {
                synchronized (priUser.get()) {
                    synchronized (secUser.get()) {
                        sendOutgoingFriendRequest(userId, friendId);
                        receivedIncomingFriendRequest(friendId, userId);
                    }
                }
            } else {
                synchronized (secUser.get()) {
                    synchronized (priUser.get()) {
                        sendOutgoingFriendRequest(userId, friendId);
                        receivedIncomingFriendRequest(friendId, userId);
                    }
                }
            }
            return 0;
        }
    }

    public String getUserId(String username, String password) {
        String userId = userRepository.getIdfromUsername(username);
        if (userId == null) return userId;

        Optional<User> curUser = userRepository.findById(userId);
        if (!curUser.isPresent()) return null;

        if (curUser.get().getPassword().equals(password)) {
            return userId;
        } else {
            return null;
        }
    }

    public void addPayment(String userId, String paymentId) {
        userRepository.getPaymentList().getOrDefault(userId, new ArrayList<>()).add(paymentId);
    }

    private void addFriend(String userId, String friendId) {
        userRepository.getFriendMap().getOrDefault(userId, new ConcurrentHashMap<>()).put(friendId, true);
    }

    public void removeFriend(String userId, String friendId) {
        userRepository.getFriendMap().getOrDefault(userId, new ConcurrentHashMap<>()).remove(friendId);
    }

    public boolean deleteIncomingFriendRequest(String userId, String friendId) {
        return userRepository.getIncomingFriendRequestMap().getOrDefault(userId, new ConcurrentHashMap<>()).remove(friendId) != null;
    }

    public boolean deleteOutgoingFriendRequest(String userId, String friendId) {
        return userRepository.getOutgoingFriendRequestMap().getOrDefault(userId, new ConcurrentHashMap<>()).remove(friendId) != null;
    }

    public boolean acceptIncomingFriendRequest(String userId, String friendId) {
        if (deleteIncomingFriendRequest(userId, friendId)) {
            if (userRepository.getIncomingFriendRequestMap().getOrDefault(userId, new ConcurrentHashMap<>()).remove(friendId) != null) {
                userRepository.getFriendMap().getOrDefault(userId, new ConcurrentHashMap<>()).put(friendId, true);
                return true;
            }
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
            userRepository.getIncomingFriendRequestMap().get(friendId).put(friendId, true);
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

    public void addCashOut(String userId, String cashId) {
        userRepository.getCashList().get(userId).add(cashId);
    }

    public boolean checkBankAccount(String userId, String bankAccountId) {
        return userRepository.getBankAccountList().get(userId).contains(bankAccountId);
    }

    public void addBankAccount(String userId, String bankAccountId) {
        userRepository.getBankAccountList().get(userId).add(bankAccountId);
    }
}
