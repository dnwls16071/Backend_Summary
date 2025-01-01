package com.example.younghanapi1.service;

import com.example.younghanapi1.controller.form.ItemForm;
import com.example.younghanapi1.entity.item.Item;
import com.example.younghanapi1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public void saveItem(Item item) {
        itemRepository.save(item);
    }

	public Item findOne(Long id) {
        return itemRepository.findByOne(id);
    }

	// JPA의 Dirty Checking
	@Transactional
	public Item updateItem(Long itemId, ItemForm itemForm) {
		Item item = itemRepository.findByOne(itemId);
		item.setPrice(itemForm.getPrice());
		item.setName(itemForm.getName());
		item.setStockQuantity(itemForm.getStockQuantity());
		return item;
	}

	public List<Item> findItems() {
		return itemRepository.findAll();
    }
}
