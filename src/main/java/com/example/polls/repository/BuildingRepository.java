package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.polls.model.Building;
import com.example.polls.model.CoffeeDetail;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
	
    @Query("SELECT v FROM Building v where v.district.id = :id")
    List<Building> findByDistrict(Long id);
}
