package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.ProductShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductShippingInfoRepository extends JpaRepository<ProductShippingInfo, Long> {
}
