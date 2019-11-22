package com.example.polls.repository;

import java.util.List;
import java.util.Optional;

import com.example.polls.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.polls.model.CoffeeDetail;
import com.example.polls.model.CoffeeOrder;

@Repository
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {

	
    List<CoffeeOrder> findTop5ByUserOrderByCreatedAtDesc(User u);

    List<CoffeeOrder> findByOrderUIDInOrderByCreatedAtDesc(List<String> ids);

    List<CoffeeOrder> findByStatusOrderByUpdatedAtDesc(int status);

    @Query(value="SELECT DISTINCT orderuid FROM coffe_order WHERE user_id = ?1 Order By created_at DESC LIMIT 5", nativeQuery = true)
    List<String> findLast5UidByUser(Long user_id);

    @Query(value="SELECT COUNT(*) FROM coffe_order WHERE status = 0", nativeQuery = true)
    Integer countActiveOrders();

}
