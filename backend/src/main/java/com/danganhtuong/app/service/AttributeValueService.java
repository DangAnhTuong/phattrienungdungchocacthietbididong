package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.AttributeValue;
import com.danganhtuong.app.repository.AttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttributeValueService {

    private final AttributeValueRepository repository;

    public List<AttributeValue> findAll() {
        return repository.findAll();
    }

    public Optional<AttributeValue> findById(UUID id) {
        return repository.findById(id);
    }

    public AttributeValue save(AttributeValue entity) {
        return repository.save(entity);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
