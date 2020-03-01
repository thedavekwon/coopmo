package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository implements CrudRepository<User, String> {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> incomingFriendRequestMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> outgoingFriendRequestMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> friendMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ArrayList<String>> paymentList = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ArrayList<String>> cashList = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ArrayList<String>> bankAccountList = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, User> db = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> usernameMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> emailMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> handleMap = new ConcurrentHashMap<>();

    @Override
    public <S extends User> S save(S s) {
        db.put(s.getId(), s);
        return s;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<User> findById(String s) {
        return Optional.ofNullable(db.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return db.containsKey(s);
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return db.size();
    }

    @Override
    public void deleteById(String s) {
        db.remove(s);
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {

    }

    // https://stackoverflow.com/questions/47123556/get-and-remove-all-current-entries-from-a-concurrentmap
    @Override
    public void deleteAll() {
    }

    public String getIdfromUsername(String username) {
        if (usernameMap.containsKey(username)) return usernameMap.get(username);
        return null;
    }

    public boolean insertUsername
            (String username, String id) {
        if (usernameMap.containsKey(username) && !usernameMap.get(username).equals(id))
            return false;
        else {
            usernameMap.put(username, id);
            return true;
        }
    }

    public boolean changeUsername
            (String username, String newUsername
                    , String id) {
        synchronized (usernameMap) {
            if (username.equals(newUsername
            ))
                return true;
            else if (usernameMap.containsKey(username) && !usernameMap.containsKey(newUsername
            )) {
                usernameMap.remove(username);
                usernameMap.put(newUsername
                        , id);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean containsUsername
            (String username) {
        return usernameMap.containsKey(username);
    }

    public boolean insertEmail(String email, String id) {
        if (emailMap.containsKey(email) && !emailMap.get(email).equals(id))
            return false;
        else {
            emailMap.put(email, id);
            return true;
        }
    }

    public boolean changeEmail(String email, String newEmail, String id) {
        synchronized (emailMap) {
            if (email.equals(newEmail))
                return true;
            else if (emailMap.containsKey(email) && !emailMap.containsKey(newEmail)) {
                emailMap.remove(email);
                emailMap.put(newEmail, id);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean containsEmail(String email) {
        return emailMap.containsKey(email);
    }

    public boolean insertHandle(String handle, String id) {
        if (handleMap.containsKey(handle) && !handleMap.get(handle).equals(id))
            return false;
        else {
            handleMap.put(handle, id);
            return true;
        }
    }

    public boolean changeHandle(String handle, String newHandle, String id) {
        synchronized (handleMap) {
            if (handle.equals(newHandle))
                return true;
            else if (handleMap.containsKey(handle) && !handleMap.containsKey(newHandle)) {
                handleMap.remove(handle);
                handleMap.put(newHandle, id);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean containsHandle(String handle) {
        return handleMap.containsKey(handle);
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> getIncomingFriendRequestMap() {
        return incomingFriendRequestMap;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> getOutgoingFriendRequestMap() {
        return outgoingFriendRequestMap;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> getFriendMap() {
        return friendMap;
    }

    public ConcurrentHashMap<String, ArrayList<String>> getPaymentList() {
        return paymentList;
    }

    public ConcurrentHashMap<String, ArrayList<String>> getCashList() {
        return cashList;
    }

    public ConcurrentHashMap<String, ArrayList<String>> getBankAccountList() {
        return bankAccountList;
    }
}
