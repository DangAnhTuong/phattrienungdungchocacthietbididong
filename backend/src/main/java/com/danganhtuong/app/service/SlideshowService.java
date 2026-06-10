package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.Slideshow;
import com.danganhtuong.app.repository.SlideshowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SlideshowService {

    private final SlideshowRepository repository;

    public List<Slideshow> findAll() {
        return repository.findAll();
    }

    public Optional<Slideshow> findById(Long id) {
        return repository.findById(id);
    }

    public Slideshow save(Slideshow entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
