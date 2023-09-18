package com.FaheDevs.orderservice.repository;


import com.FaheDevs.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
