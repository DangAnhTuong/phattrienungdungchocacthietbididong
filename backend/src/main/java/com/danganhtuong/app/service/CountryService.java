package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.Country;
import com.danganhtuong.app.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository repository;

    public List<Country> findAll() {
        return repository.findAll();
    }

    public Optional<Country> findById(Long id) {
        return repository.findById(id);
    }

    public Country save(Country entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
