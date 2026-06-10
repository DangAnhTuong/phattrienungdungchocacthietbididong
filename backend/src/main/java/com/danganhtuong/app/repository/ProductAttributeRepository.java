package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
}
