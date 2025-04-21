package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByCode(String code);
    List<Item> findByType(Item.ItemType type);
    Page<Item> findByDescriptionContaining(String description, Pageable pageable);
    boolean existsByCode(String code);
    //Item findByName(String code);
}

