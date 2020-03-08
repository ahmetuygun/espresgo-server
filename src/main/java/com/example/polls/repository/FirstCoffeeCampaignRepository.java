package com.example.polls.repository;

import com.example.polls.model.Adress;
import com.example.polls.model.FirstCoffeeCampaign;
import com.example.polls.model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FirstCoffeeCampaignRepository extends JpaRepository<FirstCoffeeCampaign, Long> {

    @Query("SELECT v FROM FirstCoffeeCampaign v where v.user.id = :id and v.campaignCode = :code and v.status = :status ")
    List<FirstCoffeeCampaign> findByUser(Long id, String code, String status);

}
