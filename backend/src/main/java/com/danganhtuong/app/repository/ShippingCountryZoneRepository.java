package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.ShippingCountryZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ShippingCountryZoneRepository extends JpaRepository<ShippingCountryZone, Long> {
}
