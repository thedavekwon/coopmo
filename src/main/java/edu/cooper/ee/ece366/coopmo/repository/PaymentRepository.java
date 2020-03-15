package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends PagingAndSortingRepository<Payment, String> {

    @Query("SELECT p FROM Payment p WHERE p.type = 0 ORDER BY p.timestamp DESC")
    List<Payment> getLatestPublicPayments();

//    private static final ConcurrentSkipListMap<String, Payment> db = new ConcurrentSkipListMap<>();
//
//    public ConcurrentSkipListMap<String, Payment> getPaymentMap() {
//        return db;
//    }
//
//    @Override
//    public <S extends Payment> S save(S s) {
//        db.put(s.getId(), s);
//        return s;
//    }
//
//    @Override
//    public <S extends Payment> Iterable<S> saveAll(Iterable<S> iterable) {
//        return null;
//    }
//
//    @Override
//    public Optional<Payment> findById(String s) {
//        return Optional.ofNullable(db.get(s));
//    }
//
//    @Override
//    public boolean existsById(String s) {
//        return db.containsKey(s);
//    }
//
//    @Override
//    public Iterable<Payment> findAll() {
//        return null;
//    }
//
//    @Override
//    public Iterable<Payment> findAllById(Iterable<String> iterable) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return db.size();
//    }
//
//    @Override
//    public void deleteById(String s) {
//        db.remove(s);
//    }
//
//    @Override
//    public void delete(Payment payment) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Payment> iterable) {
//
//    }
//
//    // https://stackoverflow.com/questions/47123556/get-and-remove-all-current-entries-from-a-concurrentmap
//    @Override
//    public void deleteAll() {
//        db.entrySet()
//                .stream()
//                .filter(e -> db.remove(e.getKey(), e.getValue()));
//    }
}
