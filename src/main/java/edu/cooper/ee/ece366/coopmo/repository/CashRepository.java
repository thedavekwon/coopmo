package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.Cash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CashRepository implements CrudRepository<Cash, String> {
    private static final ConcurrentHashMap<String, Cash> db = new ConcurrentHashMap<>();

    @Override
    public <S extends Cash> S save(S s) {
        db.put(s.getId(), s);
        return s;
    }

    @Override
    public <S extends Cash> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Cash> findById(String s) {
        return Optional.ofNullable(db.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return db.containsKey(s);
    }

    @Override
    public Iterable<Cash> findAll() {
        return null;
    }

    @Override
    public Iterable<Cash> findAllById(Iterable<String> iterable) {
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
    public void delete(Cash cash) {

    }

    @Override
    public void deleteAll(Iterable<? extends Cash> iterable) {

    }

    // https://stackoverflow.com/questions/47123556/get-and-remove-all-current-entries-from-a-concurrentmap
    @Override
    public void deleteAll() {
        db.entrySet()
                .stream()
                .filter(e -> db.remove(e.getKey(), e.getValue()));
    }
}
