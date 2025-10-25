package com.jwj.db2_start.config;

import com.jwj.db2_start.repository.ItemRepository;
import com.jwj.db2_start.repository.memory.MemoryItemRepository;
import com.jwj.db2_start.service.ItemService;
import com.jwj.db2_start.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MemoryConfig {

    //@Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    //@Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }

}
