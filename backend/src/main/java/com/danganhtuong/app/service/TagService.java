package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.Tag;
import com.danganhtuong.app.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;

    public List<Tag> findAll() {
        return repository.findAll();
    }

    public Optional<Tag> findById(UUID id) {
        return repository.findById(id);
    }

    public Tag save(Tag entity) {
        return repository.save(entity);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
