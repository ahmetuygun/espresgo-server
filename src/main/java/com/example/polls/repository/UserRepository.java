package com.example.polls.repository;

import com.example.polls.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by ahmetuygun
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByPhone(String username);

    Boolean existsByPhoneAndStatus(String phone, Integer status);

    Boolean existsByEmailAndStatus(String email, Integer status);

    Optional<User>  findByEmailAndPhoneAndStatus(String email, String phone, Integer status);



}
