package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {
}
