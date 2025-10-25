package com.example.younghanapi1.service;

import com.example.younghanapi1.entity.Delivery;
import com.example.younghanapi1.entity.Member;
import com.example.younghanapi1.entity.Order;
import com.example.younghanapi1.entity.OrderItem;
import com.example.younghanapi1.entity.item.Item;
import com.example.younghanapi1.repository.ItemRepository;
import com.example.younghanapi1.repository.MemberRepository;
import com.example.younghanapi1.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	@Transactional
	public Long order(Long memberId, Long itemId, int count) {

		Member member = memberRepository.findByOne(memberId);
		Item item = itemRepository.findByOne(itemId);

		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());

		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		Order order = Order.createOrder(member, delivery, orderItem);
		orderRepository.save(order);
		return order.getId();
	}

	@Transactional
	public void cancel(Long orderId) {
		Order order = orderRepository.findByOne(orderId);
		order.cancel();
	}
}

