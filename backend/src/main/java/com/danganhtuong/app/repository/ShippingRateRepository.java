package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.ShippingRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ShippingRateRepository extends JpaRepository<ShippingRate, Long> {
}
