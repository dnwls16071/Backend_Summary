package com.jwj.springTestCode.spring.repository;

import com.jwj.springTestCode.spring.OrderStatus;
import com.jwj.springTestCode.spring.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("select o from Order o where o.registeredDateTime >= :startDateTime and o.registeredDateTime < :endDateTime and o.orderStatus = :orderStatus")
	List<Order> findOrdersByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime, OrderStatus orderStatus);
}
