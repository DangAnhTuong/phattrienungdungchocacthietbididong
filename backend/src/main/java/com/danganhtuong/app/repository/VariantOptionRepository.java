package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.VariantOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface VariantOptionRepository extends JpaRepository<VariantOption, Long> {
}
