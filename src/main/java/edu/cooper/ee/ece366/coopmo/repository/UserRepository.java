package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository implements CrudRepository<User, String> {
    private static final ConcurrentHashMap<String, User> db = new ConcurrentHashMap<>();

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
        db.entrySet()
                .stream()
                .filter(e -> db.remove(e.getKey(), e.getValue()));
    }
}
