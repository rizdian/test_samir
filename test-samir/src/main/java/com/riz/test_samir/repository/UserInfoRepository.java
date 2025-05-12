package com.riz.test_samir.repository;

import com.riz.test_samir.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
