package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductIdOrderByCreatedAtDesc(UUID productId);

    @org.springframework.data.jpa.repository.Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.user.id = :userId")
    Optional<Review> findByProductIdAndUserId(
            @org.springframework.data.repository.query.Param("productId") UUID productId, 
            @org.springframework.data.repository.query.Param("userId") Long userId
    );
}
