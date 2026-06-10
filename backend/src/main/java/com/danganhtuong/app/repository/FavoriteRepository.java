package com.danganhtuong.app.repository;

import com.danganhtuong.app.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Favorite> findByUserIdAndProductId(Long userId, UUID productId);
    Optional<Favorite> findByUserIdAndProductIdAndSizeAndColor(Long userId, UUID productId, String size, String color);
}
