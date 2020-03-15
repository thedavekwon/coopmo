package edu.cooper.ee.ece366.coopmo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// https://stackoverflow.com/questions/51998450/auto-generate-string-primary-key-with-annotation-in-hibernate
// String UUID Id
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String handle;

    @Column(nullable = false)
    private long balance;

    @Column(nullable = false)
    private boolean deleted;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Payment> fromPaymentSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Payment> toPaymentSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Cash> cashSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<BankAccount> bankAccountSet;

    @ManyToMany
    @JsonBackReference
    @JoinTable
    private Set<User> friendSet;

    @ManyToMany
    @JsonBackReference
    @JoinTable
    private Set<User> outgoingFriendRequestSet;

    @ManyToMany
    @JsonBackReference
    @JoinTable
    private Set<User> incomingFriendRequestSet;

    public User() {
    }

    public User(String _name, String _username, String _password, String _email, String _handle) {
        name = _name;
        username = _username;
        password = _password;
        email = _email;
        handle = _handle;
        deleted = false;
        friendSet = new HashSet<>();
        bankAccountSet = new HashSet<>();
        cashSet = new HashSet<>();
        toPaymentSet = new HashSet<>();
        fromPaymentSet = new HashSet<>();
        incomingFriendRequestSet = new HashSet<>();
        outgoingFriendRequestSet = new HashSet<>();
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
        return balance;
    }

    public void incrementBalance(long amount) {
        balance = balance + amount;
    }

    public void decrementBalance(long amount) {
        balance = balance - amount;
    }

    public void addFriend(User friend) {
        friendSet.add(friend);
    }

    public void deleteFriend(User friend) {
        friendSet.remove(friend);
    }

    public void addOutgoingFriendRequest(User friend) {
        outgoingFriendRequestSet.add(friend);
    }

    public boolean removeOutgoingFriendRequest(User friend) {
        return outgoingFriendRequestSet.remove(friend);
    }

    public void addIncomingFriendRequest(User friend) {
        incomingFriendRequestSet.add(friend);
    }

    public boolean removeIncomingFriendRequest(User friend) {
        return incomingFriendRequestSet.remove(friend);
    }

    public boolean isFriend(User friend) {
        return friendSet.contains(friend);
    }

    public boolean isOutgoingFriend(User friend) {
        return outgoingFriendRequestSet.contains(friend);
    }

    public boolean isIncomingFriend(User friend) {
        return incomingFriendRequestSet.contains(friend);
    }

    public Set<BankAccount> getBankAccount() {
        return bankAccountSet;
    }

    public Set<User> getOutgoingFriendRequestSet() {
        return outgoingFriendRequestSet;
    }

    public Set<User> getIncomingFriendRequestSet() {
        return incomingFriendRequestSet;
    }

    public Set<User> getFriendSet() {
        return friendSet;
    }

    public void addBankAccount(BankAccount bankAccount) {
        bankAccountSet.add(bankAccount);
    }

    public void addCash(Cash cash) {
        cashSet.add(cash);
    }

    public Set<Cash> getCashSet() {
        return cashSet;
    }

    public void addFromPayment(Payment payment) {
        fromPaymentSet.add(payment);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Set<Payment> getFromPaymentSet() {
        return fromPaymentSet;
    }

    public Set<Payment> getToPaymentSet() {
        return toPaymentSet;
    }

    public Set<BankAccount> getBankAccountSet() {
        return bankAccountSet;
    }

    public void addToPayment(Payment payment) {
        toPaymentSet.add(payment);
    }
}
