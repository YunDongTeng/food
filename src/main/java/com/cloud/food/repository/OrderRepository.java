package com.cloud.food.repository;

import com.cloud.food.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order,String> {

    Page<Order> findByBuyerOpenid(String openId, Pageable pageable);

}
