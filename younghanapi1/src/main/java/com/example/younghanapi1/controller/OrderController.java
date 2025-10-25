package com.example.younghanapi1.controller;

import com.example.younghanapi1.entity.Member;
import com.example.younghanapi1.entity.Order;
import com.example.younghanapi1.entity.item.Item;
import com.example.younghanapi1.repository.OrderRepository;
import com.example.younghanapi1.repository.OrderSearch;
import com.example.younghanapi1.service.ItemService;
import com.example.younghanapi1.service.MemberService;
import com.example.younghanapi1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderRepository orderRepository;
	private final OrderService orderService;
	private final MemberService memberService;
	private final ItemService itemService;

	@GetMapping("/order")
	public String createForm(Model model) {
		List<Member> members = memberService.findMembers();
		List<Item> items = itemService.findItems();

		model.addAttribute("members", members);
		model.addAttribute("items", items);
		return "order/orderForm";
	}

	// 식별자를 넘겨서 Dirty Checking
	@PostMapping("/order")
	public String createOrder(@RequestParam(name = "memberId") Long memberId,
							  @RequestParam(name = "itemId") Long itemId,
							  @RequestParam(name = "count") int count) {
		orderService.order(memberId, itemId, count);
		return "redirect:/orders";
	}

	@GetMapping("/orders")
	public String orderList(@ModelAttribute(name = "orderSearch") OrderSearch orderSearch, Model model) {
		List<Order> orders = orderRepository.findAll(orderSearch);
		model.addAttribute("orders", orders);
		return "order/orderList";
	}
}
