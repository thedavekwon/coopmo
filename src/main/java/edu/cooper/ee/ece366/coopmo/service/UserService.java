package edu.cooper.ee.ece366.coopmo.service;

import edu.cooper.ee.ece366.coopmo.model.User;
import edu.cooper.ee.ece366.coopmo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean editUsername(String id, String newUname) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) return false;
        if (userRepository.changeUname(user.get().getUsername(), newUname, id)) {
            user.get().setUsername(newUname);
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

    // -1 is no user with id exists
    // -2 is Username exists already
    // -3 Email already exists
    // -4 Handle already exists
    public ArrayList<Integer> editProfile(String id, String newName, String newUname, String newPassword,
                                          String newEmail, String newHandle) {
        ArrayList<Integer> errors = new ArrayList<>();
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            errors.add(-1);
            return errors;
        } else {
            user.get().setName(newName);
            user.get().setPassword(newPassword);
            if (editUsername(id, newUname))
                errors.add(-2);
            if (editEmail(id, newEmail))
                errors.add(-3);
            if (editHandle(id, newHandle))
                errors.add(-4);
            return errors;
        }
    }

    // This is also used to decline Friend Requests
    // -1 is user not found
    // -2 if friend not found in incoming friend request
    // 0 if success
    public int cancelOutgoingFriendRequest(String id, String friendId) {
        Optional<User> priUser = userRepository.findById(id);
        Optional<User> secUser = userRepository.findById(friendId);

        if (!priUser.isPresent() || !secUser.isPresent()) {
            return -1;
        } else {
            //if compare to returns pos use that user first
            //else use other
            if (id.compareTo(friendId) > 0) {
                synchronized (priUser.get()) {
                    synchronized (secUser.get()) {
                        priUser.get().deleteOutgoingFriendRequest(friendId);
                        secUser.get().deleteIncomingFriendRequest(id);
                    }
                }
            } else {
                synchronized (secUser.get()) {
                    synchronized (priUser.get()) {
                        priUser.get().deleteOutgoingFriendRequest(friendId);
                        secUser.get().deleteIncomingFriendRequest(id);
                    }
                }
            }
            return 0;
        }
    }

    // -1 if user not found
    public int acceptIncomingRequest(String id, String friendId) {
        Optional<User> priUser = userRepository.findById(id);
        Optional<User> secUser = userRepository.findById(friendId);

        if (!priUser.isPresent() || !secUser.isPresent()) {
            return -1;
        } else {
            //if compare to returns pos use that user first
            //else use other
            if (id.compareTo(friendId) > 0) {
                synchronized (priUser.get()) {
                    synchronized (secUser.get()) {
                        if (!priUser.get().acceptIncomingFriendRequest(friendId))
                            return -2;
                        secUser.get().acceptedOutgoingFriendRequest(id);
                    }
                }
            } else {
                synchronized (secUser.get()) {
                    synchronized (priUser.get()) {
                        if (!priUser.get().acceptIncomingFriendRequest(friendId))
                            return -2;
                        secUser.get().acceptedOutgoingFriendRequest(id);
                    }
                }
            }
            return 0;
        }
    }

    // -1 if users not found
    // -2 if already friends
    public int sendOutgoingFriendRequest(String id, String friendId) {
        Optional<User> priUser = userRepository.findById(id);
        Optional<User> secUser = userRepository.findById(friendId);

        if (!priUser.isPresent() || !secUser.isPresent()) {
            return -1;
        } else {
            //if compare to returns pos use that user first
            //else use other
            if (id.compareTo(friendId) > 0) {
                synchronized (priUser.get()) {
                    synchronized (secUser.get()) {
                        priUser.get().sendOutgoingFriendRequest(friendId);
                        secUser.get().receivedIncomingFriendRequest(id);
                    }
                }
            } else {
                synchronized (secUser.get()) {
                    synchronized (priUser.get()) {
                        priUser.get().sendOutgoingFriendRequest(friendId);
                        secUser.get().receivedIncomingFriendRequest(id);
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
}
