package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.Coupon;
import com.danganhtuong.app.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository repository;

    public List<Coupon> findAll() {
        return repository.findAll();
    }

    public Optional<Coupon> findById(Long id) {
        return repository.findById(id);
    }

    public Coupon save(Coupon entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
