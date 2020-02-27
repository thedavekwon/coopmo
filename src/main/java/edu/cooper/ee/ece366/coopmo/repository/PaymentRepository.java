package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PaymentRepository implements CrudRepository<Payment, String> {
    private static final ConcurrentHashMap<String, Payment> db = new ConcurrentHashMap<>();

    @Override
    public <S extends Payment> S save(S s) {
        db.put(s.getId(), s);
        return s;
    }

    @Override
    public <S extends Payment> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Payment> findById(String s) {
        return Optional.ofNullable(db.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Payment> findAll() {
        return null;
    }

    @Override
    public Iterable<Payment> findAllById(Iterable<String> iterable) {
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
    public void delete(Payment payment) {

    }

    @Override
    public void deleteAll(Iterable<? extends Payment> iterable) {

    }

    // https://stackoverflow.com/questions/47123556/get-and-remove-all-current-entries-from-a-concurrentmap
    @Override
    public void deleteAll() {
        db.entrySet()
                .stream()
                .filter(e -> db.remove(e.getKey(), e.getValue()));
    }
}
