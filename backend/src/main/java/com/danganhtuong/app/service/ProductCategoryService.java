package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.ProductCategory;
import com.danganhtuong.app.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository repository;

    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    public Optional<ProductCategory> findById(Long id) {
        return repository.findById(id);
    }

    public ProductCategory save(ProductCategory entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
