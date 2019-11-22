package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.polls.model.CoffeeDetail;
import com.example.polls.model.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("SELECT v FROM District v where v.city.id = :id")
    List<District> findByCity(@Param("id")  Long id);
}
