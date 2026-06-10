package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.Slideshow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SlideshowRepository extends JpaRepository<Slideshow, Long> {
}
