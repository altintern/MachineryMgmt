package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> findAll();
    Page<Item> findAll(Pageable pageable);
    Optional<Item> findById(Long id);
    Optional<Item> findByCode(String code);
    List<Item> findByType(Item.ItemType type);
    Page<Item> findByDescriptionContaining(String description, Pageable pageable);
    Item save(Item item);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByCode(String code);
    List<ItemDto> findAllDto();
    Page<ItemDto> findAllDto(Pageable pageable);
    Optional<ItemDto> findDtoById(Long id);
    List<ItemDto> findDtoByType(Item.ItemType type);
}

