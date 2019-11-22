package com.example.polls.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Adress;

@Repository
public interface AddressRepository extends JpaRepository<Adress, Long> {

    @Query("SELECT v FROM Adress v where v.user.id = :id")
    Optional<Adress> findByUser(Long id);
}
