package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.ProductTag;
import com.danganhtuong.app.repository.ProductTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTagService {

    private final ProductTagRepository repository;

    public List<ProductTag> findAll() {
        return repository.findAll();
    }

    public Optional<ProductTag> findById(Long id) {
        return repository.findById(id);
    }

    public ProductTag save(ProductTag entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
