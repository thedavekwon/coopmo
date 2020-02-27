package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BankAccountRepository implements CrudRepository<BankAccount, String> {
    private static final ConcurrentHashMap<String, BankAccount> db = new ConcurrentHashMap<>();

    @Override
    public <S extends BankAccount> S save(S s) {
        db.put(s.getId(), s);
        return s;
    }

    @Override
    public <S extends BankAccount> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<BankAccount> findById(String s) {
        return Optional.ofNullable(db.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<BankAccount> findAll() {
        return null;
    }

    @Override
    public Iterable<BankAccount> findAllById(Iterable<String> iterable) {
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
    public void delete(BankAccount bankAccount) {

    }

    @Override
    public void deleteAll(Iterable<? extends BankAccount> iterable) {

    }

    @Override
    public void deleteAll() {
        db.entrySet()
                .stream()
                .filter(e -> db.remove(e.getKey(), e.getValue()));
    }
}
