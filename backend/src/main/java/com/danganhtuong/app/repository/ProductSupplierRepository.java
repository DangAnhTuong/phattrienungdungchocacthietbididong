package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {
}
