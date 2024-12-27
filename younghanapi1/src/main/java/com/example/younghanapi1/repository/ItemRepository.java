package com.example.younghanapi1.repository;

import com.example.younghanapi1.entity.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager entityManager;

	public void save(Item item) {
		if (item.getId() == null) {
			entityManager.persist(item);
		} else {
			entityManager.merge(item);
		}
	}

	public Item findByOne(Long id) {
		return entityManager.find(Item.class, id);
	}

	public List<Item> findAll() {
		return entityManager.createQuery("SELECT i FROM Item i", Item.class).getResultList();
	}
}
