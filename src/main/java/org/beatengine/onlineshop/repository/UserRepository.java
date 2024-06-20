package org.beatengine.onlineshop.repository;

import org.beatengine.onlineshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * User Queries
 */
@Repository
public interface UserRepository
        extends JpaRepository<User, BigInteger> {

    <S extends User> S save(S entity);

    List<User> findAll();

    Long countByEmail(final String email);
  
    void deleteById(BigInteger primaryKey);

    User findByEmail(String email);
}
