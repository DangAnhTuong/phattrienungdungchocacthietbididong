package com.danganhtuong.app.controller;

import com.danganhtuong.app.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getFavorites() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(favoriteService.getUserFavorites(auth.getName()));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addFavorite(
            @PathVariable UUID productId,
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "color", required = false) String color) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        favoriteService.addFavorite(auth.getName(), productId, size, color);
        return ResponseEntity.ok("Added to favorites");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeFavorite(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        favoriteService.removeFavorite(auth.getName(), id);
        return ResponseEntity.ok("Removed from favorites");
    }

    @GetMapping("/{productId}/check")
    public ResponseEntity<Boolean> checkFavorite(@PathVariable UUID productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(favoriteService.isFavorite(auth.getName(), productId));
    }
}
