package com.example.younghanapi1.entity;

import com.example.younghanapi1.entity.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

	@Id
	@GeneratedValue
	@Column(name = "order_item_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	private int orderPrice;

	private int count;

	public void cancel() {
		getItem().addStock(count);
	}

	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		item.removeStock(count);
		return orderItem;
	}
}