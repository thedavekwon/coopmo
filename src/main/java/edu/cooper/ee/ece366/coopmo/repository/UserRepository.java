package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
//    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> incomingFriendRequestMap = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> outgoingFriendRequestMap = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> friendMap = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<String, ArrayList<String>> paymentListMap = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<String, ArrayList<String>> cashListMap = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<String, ArrayList<String>> bankAccountListMap = new ConcurrentHashMap<>();

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    String getIdfromUsername(String username);

    @Query("SELECT CASE WHEN count(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.deleted = FALSE")
    boolean containsEmail(String email);

    @Query("SELECT CASE WHEN count(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username AND u.deleted = FALSE")
    boolean containsUsername(String username);

    @Query("SELECT CASE WHEN count(u) > 0 THEN true ELSE false END FROM User u WHERE u.handle = :handle AND u.deleted = FALSE")
    boolean containsHandle(String handle);

//    public boolean areFriends(String userId, String friendId) {
//        synchronized (friendMap) {
//            return (friendMap.containsKey(userId) && friendMap.get(userId).containsKey(friendId)) &&
//                    (friendMap.containsKey(friendId) && friendMap.get(friendId).containsKey(userId));
//        }
//    }
//
//    // -2 if not friends
//    public int deleteFriends(String userId, String friendId) {
//        if (areFriends(userId, friendId)) {
//            synchronized (friendMap) {
//                friendMap.get(userId).remove(friendId);
//                friendMap.get(friendId).remove(userId);
//                return 0;
//            }
//        } else
//            return -2;
//    }
}
