package edu.cooper.ee.ece366.coopmo.model;

import org.springframework.data.annotation.Id;

import java.util.concurrent.atomic.AtomicLong;

public class User {

    private final AtomicLong balance = new AtomicLong();
    @Id
    public String id;
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String email;
    public String handle;

    public User() {
    }

    // TODO()
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        balance.set(0);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getBalance() {
        return balance.get();
    }

    public void setBalance(long newBalance) {
        balance.getAndSet(newBalance);
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']", id, firstName, lastName);
    }
}
