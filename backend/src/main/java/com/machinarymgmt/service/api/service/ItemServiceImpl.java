package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.ItemRepository;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.dto.ItemDto;
import com.machinarymgmt.service.api.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    
    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
    
    @Override
    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }
    
    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }
    
    @Override
    public Optional<Item> findByCode(String code) {
        return Optional.ofNullable(itemRepository.findByCode(code));
    }
    
    @Override
    public List<Item> findByType(Item.ItemType type) {
        return itemRepository.findByType(type);
    }
    
    @Override
    public Page<Item> findByDescriptionContaining(String description, Pageable pageable) {
        return itemRepository.findByDescriptionContaining(description, pageable);
    }
    
    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }
    
    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return itemRepository.existsById(id);
    }
    
    @Override
    public boolean existsByCode(String code) {
        return itemRepository.existsByCode(code);
    }
    
    @Override
    public List<ItemDto> findAllDto() {
        return itemMapper.toDtoList(findAll());
    }
    
    @Override
    public Page<ItemDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(itemMapper::toDto);
    }
    
    @Override
    public Optional<ItemDto> findDtoById(Long id) {
        return findById(id).map(itemMapper::toDto);
    }
    
    @Override
    public List<ItemDto> findDtoByType(Item.ItemType type) {
        return findByType(type).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }
}

