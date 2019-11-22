package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.polls.model.CoffeeDetail;
import com.example.polls.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT v FROM Company v where v.building.id = :id")
    List<Company> findByBuilding(Long id);
}
