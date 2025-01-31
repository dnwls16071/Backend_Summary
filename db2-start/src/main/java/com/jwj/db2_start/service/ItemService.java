package com.jwj.db2_start.service;

import com.jwj.db2_start.domain.Item;
import com.jwj.db2_start.repository.ItemSearchCond;
import com.jwj.db2_start.repository.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);
}
