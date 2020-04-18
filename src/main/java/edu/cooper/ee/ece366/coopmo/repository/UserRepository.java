package edu.cooper.ee.ece366.coopmo.repository;

import edu.cooper.ee.ece366.coopmo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    String getIdfromUsername(String username);

    @Query("SELECT CASE WHEN count(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.deleted = FALSE")
    boolean containsEmail(String email);

    @Query("SELECT CASE WHEN count(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username AND u.deleted = FALSE")
    boolean containsUsername(String username);

    @Query("SELECT CASE WHEN count(u) > 0 THEN true ELSE false END FROM User u WHERE u.handle = :handle AND u.deleted = FALSE")
    boolean containsHandle(String handle);

    Optional<User> findByUsername(String username);

    Set<User> findByUsernameStartsWith(String username_match);

    Set<User> findByHandleStartsWith(String handle_match);

    Set<User> findByEmailStartsWith(String handle_match);
}
