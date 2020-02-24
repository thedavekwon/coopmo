package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class User {

    @Id
    public Long id;

    public String name;
    public String username;
    public String password;
    public String email;
    public String handle;

    private AtomicLong balance = new AtomicLong();

    public List<Long> incomingFriendRequestList;
    public List<Long> outgoingFriendRequestList;
    public List<Long> friendList;
    public List<Long> paymentList;
    public List<Long> cashOutList;

    // TODO (ID)
    public User(Long id, String name, String username, String password, String email, String handle) {
        this.id =id;
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
    }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

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

    public long getBalance() {
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
}
