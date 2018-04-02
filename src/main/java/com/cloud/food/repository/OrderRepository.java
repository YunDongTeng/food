package com.cloud.food.repository;

import com.cloud.food.entity.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order,String> {

    List<Order> findAllByBuyerOpenid(String openId,PageRequest pageRequest);
}
