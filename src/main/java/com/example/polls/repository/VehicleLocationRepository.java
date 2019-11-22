package com.example.polls.repository;

import com.example.polls.model.Adress;

import com.example.polls.model.VehicleLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleLocationRepository extends JpaRepository<VehicleLocation, Long> {

    @Query(value="SELECT * FROM vehicle_location WHERE 1=1 Order By insert_date DESC LIMIT 1 ", nativeQuery = true)
    Optional<VehicleLocation> findLast();

}
