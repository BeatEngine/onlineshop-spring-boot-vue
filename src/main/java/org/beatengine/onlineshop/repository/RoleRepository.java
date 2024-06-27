package org.beatengine.onlineshop.repository;

import org.beatengine.onlineshop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role, BigInteger>{

    <S extends Role> S save(S entity);

    List<Role> findAll();

    void deleteById(BigInteger primaryKey);
}
