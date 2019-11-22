package com.example.polls.repository;

import com.example.polls.model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    @Query(value="SELECT * FROM UserLocation WHERE 1=1 Order By insertDate DESC ", nativeQuery = true)
    Optional<UserLocation> findLast();

}
