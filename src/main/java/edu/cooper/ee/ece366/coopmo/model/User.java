package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class User {

    @Id
    private final String id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String handle;
    private AtomicLong balance = new AtomicLong();

    public final ConcurrentHashMap<String, Boolean> incomingFriendRequestMap;
    public final ConcurrentHashMap<String, Boolean> outgoingFriendRequestMap;
    public final ConcurrentHashMap<String, Boolean> friendMap;

    private final List<String> paymentList;
    private final List<String> cashOutList;
    private final List<String> bankAccountList;


    // TODO (ID)
    public User(String name, String username, String password, String email, String handle) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.handle = handle;
        balance.set(0);


        incomingFriendRequestMap = new ConcurrentHashMap<>();
        outgoingFriendRequestMap = new ConcurrentHashMap<>();
        friendMap = new ConcurrentHashMap<>();

        paymentList = Collections.synchronizedList(new ArrayList<>());
        cashOutList = Collections.synchronizedList(new ArrayList<>());
        bankAccountList = Collections.synchronizedList(new ArrayList<>());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Long getBalance() {
        return balance.get();
    }

    public void setBalance(long newBalance) {
        balance.getAndSet(newBalance);
    }

    public void incrementBalance(long amount) {
        balance.getAndAdd(amount);
    }

    public void decrementBalance(long amount) {
        balance.getAndAdd(-amount);
    }

    public void addPayment(String transactionId) {
        paymentList.add(transactionId);
    }

    private void addFriend(String friendId) {
        friendMap.put(friendId,true);
    }

    public void removeFriend(String friendId) {
        friendMap.remove(friendId);
    }

    public boolean acceptIncomingFriendRequest(String friendId){
        if(incomingFriendRequestMap.containsKey(friendId))
        {
            incomingFriendRequestMap.remove(friendId);
            addFriend(friendId);
            return true;
        }
        else{
            // Friend not found. Should do something
            return false;
        }
    }

    // Adds incoming friend request. Returns true if no friend request sent from this user. Returns false if friend
    // request in outgoing friend request.
    public boolean receivedIncomingFriendRequest(String friendId){
        // already sent a request so just add friend
        if(outgoingFriendRequestMap.containsKey(friendId))
        {
            acceptedOutgoingFriendRequest(friendId);
            return false;
        }
        else
        {
            incomingFriendRequestMap.put(friendId, true);
            return true;
        }
    }

    public void acceptedOutgoingFriendRequest(String friendId){
        if(outgoingFriendRequestMap.containsKey(friendId))
        {
            outgoingFriendRequestMap.remove(friendId);
            addFriend(friendId);
        }
    }

    // Sends friend request. Returns true if need to send (friend request from friend isn't in incoming friend request).
    // Returns false don't need to actually send.
    public boolean sendOutgoingFriendRequest(String friendId){
        if (friendMap.containsKey(friendId)) {
            return false;
        } else if (incomingFriendRequestMap.containsKey(friendId)) {
            acceptIncomingFriendRequest(friendId);
            return false;
        } else {
            outgoingFriendRequestMap.put(friendId, true);
            return true;
        }
    }

    public void addCashOut(String cashOutId) {
        cashOutList.add(cashOutId);
    }

    public boolean checkBankAccount(String bankAccountId) {
        return bankAccountList.contains(bankAccountId);
    }

    public void addBankAccount(String bankAccountId) {
        bankAccountList.add(id);
    }
}
