package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.ProductAttributeValue;
import com.danganhtuong.app.repository.ProductAttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductAttributeValueService {

    private final ProductAttributeValueRepository repository;

    public List<ProductAttributeValue> findAll() {
        return repository.findAll();
    }

    public Optional<ProductAttributeValue> findById(UUID id) {
        return repository.findById(id);
    }

    public ProductAttributeValue save(ProductAttributeValue entity) {
        return repository.save(entity);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
