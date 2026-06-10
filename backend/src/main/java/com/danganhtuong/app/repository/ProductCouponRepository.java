package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.ProductCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductCouponRepository extends JpaRepository<ProductCoupon, Long> {
}
