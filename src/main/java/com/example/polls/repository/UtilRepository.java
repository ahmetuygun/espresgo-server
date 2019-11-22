package com.example.polls.repository;

import com.example.polls.model.Util;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilRepository extends JpaRepository<Util, Long> {

    Util findByName(String key);

}
