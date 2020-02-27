package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class User {
    private final List<String> incomingFriendRequestList;
    private final List<String> outgoingFriendRequestList;
    private final List<String> friendList;
    private final List<String> paymentList;
    private final List<String> cashOutList;
    private final List<String> bankAccountList;

    @Id
    private final String id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String handle;
    private AtomicLong balance = new AtomicLong();

    public User(String name, String username, String password, String email, String handle) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.handle = handle;
        balance.set(0);
        incomingFriendRequestList = Collections.synchronizedList(new ArrayList<>());
        outgoingFriendRequestList = Collections.synchronizedList(new ArrayList<>());
        friendList = Collections.synchronizedList(new ArrayList<>());
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

    public void addFriend(String friendId) {
        friendList.add(friendId);
    }

    public void addCashOut(String cashOutId) {
        cashOutList.add(cashOutId);
    }

    public boolean checkBankAccount(String bankId) {
        return bankAccountList.contains(bankId);
    }
}
