package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.CashOut;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CashOutRepository implements CrudRepository<CashOut, String> {
    private static final ConcurrentHashMap<String, CashOut> db = new ConcurrentHashMap<>();

    @Override
    public <S extends CashOut> S save(S s) {
        db.put(s.getId(), s);
        return s;
    }

    @Override
    public <S extends CashOut> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<CashOut> findById(String s) {
        return Optional.ofNullable(db.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<CashOut> findAll() {
        return null;
    }

    @Override
    public Iterable<CashOut> findAllById(Iterable<String> iterable) {
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
    public void delete(CashOut cashOut) {

    }

    @Override
    public void deleteAll(Iterable<? extends CashOut> iterable) {

    }

    // https://stackoverflow.com/questions/47123556/get-and-remove-all-current-entries-from-a-concurrentmap
    @Override
    public void deleteAll() {
        db.entrySet()
                .stream()
                .filter(e -> db.remove(e.getKey(), e.getValue()));
    }
}
