package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
}
