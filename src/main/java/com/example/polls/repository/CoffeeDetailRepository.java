package com.example.polls.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.polls.model.CoffeeDetail;

@Repository
public interface CoffeeDetailRepository extends JpaRepository<CoffeeDetail, Long> {

    Optional<CoffeeDetail> findById(Long id);
}
