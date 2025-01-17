package com.example.polls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.polls.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
