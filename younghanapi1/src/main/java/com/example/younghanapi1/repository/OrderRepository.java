package com.example.younghanapi1.repository;

import com.example.younghanapi1.entity.Order;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager entityManager;

	public void save(Order order) {
		entityManager.persist(order);
	}

	public Order findByOne(Long id) {
		return entityManager.find(Order.class, id);
	}

	// JPQL 스타일
	public List<Order> findAll(OrderSearch orderSearch) {
		return entityManager.createQuery("select o from Order o join o.member m " +
						" where o.member.name like :name" +
						" and o.status = :status", Order.class)
						.setParameter("name", orderSearch.getMemberName())
						.setParameter("status", orderSearch.getOrderStatus())
						.setMaxResults(1000)
						.getResultList();
	}
}
