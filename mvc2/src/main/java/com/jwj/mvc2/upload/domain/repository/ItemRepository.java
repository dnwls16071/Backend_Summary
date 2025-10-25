package com.jwj.mvc2.upload.domain.repository;

import com.jwj.mvc2.upload.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}