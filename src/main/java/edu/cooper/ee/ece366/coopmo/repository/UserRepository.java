package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository implements CrudRepository<User, String> {
    private static final ConcurrentHashMap<String, User> db = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> unameMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> emailMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> handleMap = new ConcurrentHashMap<>();



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

    public boolean insertUname(String uname, String id)
    {
        if(unameMap.containsKey(uname) && !unameMap.get(uname).equals(id))
            return false;
        else{
            unameMap.put(uname, id);
            return true;
        }
    }

    public boolean changeUname(String uname, String newUname, String id)
    {
        synchronized (unameMap) {
            if(uname.equals(newUname))
                return true;
            else if (unameMap.containsKey(uname) && !unameMap.containsKey(newUname)) {
                unameMap.remove(uname);
                unameMap.put(newUname, id);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean containsUname(String uname) { return unameMap.containsKey(uname); }

    public boolean insertEmail(String email, String id)
    {
        if(emailMap.containsKey(email) && !emailMap.get(email).equals(id))
            return false;
        else{
            emailMap.put(email, id);
            return true;
        }
    }

    public boolean changeEmail(String email, String newEmail, String id)
    {
        synchronized (emailMap) {
            if(email.equals(newEmail))
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

    public boolean containsEmail(String email) { return emailMap.containsKey(email); }

    public boolean insertHandle(String handle, String id)
    {
        if(handleMap.containsKey(handle) && !handleMap.get(handle).equals(id))
            return false;
        else{
            handleMap.put(handle, id);
            return true;
        }
    }

    public boolean changeHandle(String handle, String newHandle, String id)
    {
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


    public boolean containsHandle(String handle) { return handleMap.containsKey(handle); }


    // https://stackoverflow.com/questions/47123556/get-and-remove-all-current-entries-from-a-concurrentmap
    @Override
    public void deleteAll() {
        db.entrySet()
                .stream()
                .filter(e -> db.remove(e.getKey(), e.getValue()));
    }
}
