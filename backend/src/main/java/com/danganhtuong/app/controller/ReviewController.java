package com.danganhtuong.app.controller;


import com.danganhtuong.app.service.ReviewService;
import com.danganhtuong.app.repository.UserRepository;
import com.danganhtuong.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{productId}")
    public ResponseEntity<List<Map<String, Object>>> getReviews(@PathVariable UUID productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
    }

    @GetMapping("/{productId}/has-reviewed")
    public ResponseEntity<Boolean> hasReviewed(@PathVariable UUID productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            System.out.println("hasReviewed called but user is not authenticated");
            return ResponseEntity.ok(false);
        }
        String email = auth.getName();
        boolean result = reviewService.hasUserReviewedProduct(productId, email);
        System.out.println("hasReviewed for user " + email + " on product " + productId + " is: " + result);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/clear-all")
    public ResponseEntity<String> clearAllReviews() {
        reviewService.clearAllReviews();
        return ResponseEntity.ok("Cleared all reviews");
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> addReview(
            @PathVariable UUID productId,
            @RequestParam("rating") Double rating,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "images", required = false) List<MultipartFile> images) {
        
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            reviewService.addReview(productId, user.getId(), rating, comment, images);
            return ResponseEntity.ok("Review added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
