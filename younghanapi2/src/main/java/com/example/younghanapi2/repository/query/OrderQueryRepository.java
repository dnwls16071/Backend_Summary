package com.example.younghanapi2.repository.query;

import com.example.younghanapi2.api.dto.OrderFlatDto;
import com.example.younghanapi2.api.dto.OrderItemQueryDto;
import com.example.younghanapi2.api.dto.OrderQueryDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

	private final EntityManager em;

	public List<OrderQueryDto> findOrderItemQueryDtos() {
		List<OrderQueryDto> result = findOrders();
		result.forEach(orderQueryDto -> {
			List<OrderItemQueryDto> orderItems = findOrderItems(orderQueryDto.getOrderId());
			orderQueryDto.setOrderItems(orderItems);
		});
		return result;
	}

	// 1 : N 관계 조회
	private List<OrderItemQueryDto> findOrderItems(Long orderId) {
		return em.createQuery(
						"select new com.example.younghanapi2.api.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
								"from OrderItem oi " +
								"join oi.item i " +
								"where oi.order.id = :orderId", OrderItemQueryDto.class)
				.setParameter("orderId", orderId)
				.getResultList();
	}

	// DTO의 경우 페치 조인 적용 불가(엔티티가 아닌 데이터 전송용 객체이기 때문에)
	// 1 : N 관계(여기선, 주문과 주문상품)를 제외한 나머지 한 번에 조회
	public List<OrderQueryDto> findOrders() {
		return em.createQuery("select new com.example.younghanapi2.api.dto.OrderQueryDto(o.id, o.member.name, o.orderDate, o.status, o.delivery.address) from Order o" +
						" join o.member m" +
						" join o.delivery d", OrderQueryDto.class)
				.getResultList();
	}

	public List<OrderQueryDto> findAllByDto_optimization() {
		List<OrderQueryDto> orders = findOrders();
		List<Long> orderIds = orders.stream().map(OrderQueryDto::getOrderId).toList();	// orderId 리스트를 스트림을 사용해서 뽑아내기

		List<OrderItemQueryDto> orderItems = em.createQuery(
						"select new com.example.younghanapi2.api.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
								"from OrderItem oi " +
								"join oi.item i " +
								"where oi.order.id in :orderIds", OrderItemQueryDto.class)    // IN 절을 사용하여 한 번에 조회(N + 1 문제 개선)
				.setParameter("orderIds", orderIds)
				.getResultList();

		orders.forEach(orderQueryDto -> orderQueryDto.setOrderItems(orderItems));
		return orders;
	}

	public List<OrderFlatDto> findAllByDto_flat() {
		return em.createQuery(
				"select new com.example.younghanapi2.api.dto.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) " +
						" from Order o" +
						" join o.member m" +
						" join o.delivery d" +
						" join o.orderItems oi" +
						" join oi.item i", OrderFlatDto.class)
				.getResultList();
	}
}

