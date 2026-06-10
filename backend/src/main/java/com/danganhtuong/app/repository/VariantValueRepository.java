package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.VariantValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface VariantValueRepository extends JpaRepository<VariantValue, Long> {
}
