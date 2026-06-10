package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.Favorite;
import com.danganhtuong.app.entity.Product;
import com.danganhtuong.app.entity.ProductCategory;
import com.danganhtuong.app.entity.User;
import com.danganhtuong.app.repository.FavoriteRepository;
import com.danganhtuong.app.repository.ProductRepository;
import com.danganhtuong.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Map<String, Object>> getUserFavorites(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<Favorite> favorites = favoriteRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return favorites.stream().map(f -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", f.getId());
            map.put("size", f.getSize());
            map.put("color", f.getColor());
            
            Product p = f.getProduct();
            map.put("productId", p.getId());
            map.put("productName", p.getProductName());
            map.put("brand", p.getBrand());
            map.put("price", p.getSalePrice() != null ? p.getSalePrice() : p.getComparePrice());
            map.put("originalPrice", p.getComparePrice());
            map.put("rating", p.getRating());
            map.put("ratingCount", p.getRatingCount());
            map.put("isNewBadge", p.getIsNewBadge());
            map.put("discountTag", p.getDiscountTag());
            
            if (p.getImageUrl() != null && !p.getImageUrl().startsWith("http")) {
                if (p.getImageUrl().startsWith("assets/")) {
                    map.put("imageUrl", p.getImageUrl());
                } else {
                    map.put("imageUrl", baseUrl + "/uploads/" + p.getImageUrl());
                }
            } else {
                map.put("imageUrl", p.getImageUrl());
            }
            
            List<String> categories = p.getProductCategories().stream()
                    .map(pc -> pc.getCategory().getCategoryName())
                    .collect(Collectors.toList());
            map.put("categories", categories);

            return map;
        }).collect(Collectors.toList());
    }

    public void addFavorite(String email, UUID productId, String size, String color) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        
        Optional<Favorite> existing = favoriteRepository.findByUserIdAndProductIdAndSizeAndColor(user.getId(), productId, size, color);
        if (existing.isPresent()) {
            return; // Already added
        }
        
        Favorite favorite = Favorite.builder()
                .user(user)
                .product(product)
                .size(size)
                .color(color)
                .build();
        favoriteRepository.save(favorite);
    }

    public void removeFavorite(String email, Long favoriteId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Favorite favorite = favoriteRepository.findById(favoriteId).orElseThrow(() -> new RuntimeException("Favorite not found"));
        if (!favorite.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized to remove this favorite");
        }
        favoriteRepository.delete(favorite);
    }

    public boolean isFavorite(String email, UUID productId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return favoriteRepository.findByUserIdAndProductId(user.getId(), productId).isPresent();
    }
}
