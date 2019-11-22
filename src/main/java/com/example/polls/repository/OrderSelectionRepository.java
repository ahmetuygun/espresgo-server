package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.polls.model.CoffeeDetail;
import com.example.polls.model.CoffeeOrder;
import com.example.polls.model.OrderSelection;

@Repository
public interface OrderSelectionRepository extends JpaRepository<OrderSelection, Long> {

    Optional<OrderSelection> findByCoffeeOrder(Long order);
    
    @Query("SELECT v FROM OrderSelection v where v.coffeeOrder.user.id = :id")
    List<OrderSelection> findByUserId(@Param("id") Long id);

}
