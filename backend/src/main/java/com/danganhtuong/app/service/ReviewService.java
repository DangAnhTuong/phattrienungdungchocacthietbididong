package com.danganhtuong.app.service;


import com.danganhtuong.app.entity.Product;
import com.danganhtuong.app.entity.Review;
import com.danganhtuong.app.entity.ReviewImage;
import java.util.Map;
import com.danganhtuong.app.entity.User;
import com.danganhtuong.app.repository.ProductRepository;
import com.danganhtuong.app.repository.ReviewRepository;
import com.danganhtuong.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private final String UPLOAD_DIR = "uploads/";

    public List<Map<String, Object>> getReviewsByProductId(UUID productId) {
        List<Review> reviews = reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");

        return reviews.stream().map(r -> {
            Map<String, Object> dto = new java.util.HashMap<>();
            dto.put("id", r.getId());
            
            String userName = r.getUser().getName() != null ? r.getUser().getName() : "Anonymous";
            dto.put("userName", userName);
            
            try {
                dto.put("avatar", "https://ui-avatars.com/api/?name=" + java.net.URLEncoder.encode(userName, "UTF-8") + "&background=random");
            } catch (Exception e) {
                dto.put("avatar", "https://ui-avatars.com/api/?name=User&background=random");
            }
            
            dto.put("rating", r.getRating());
            dto.put("comment", r.getComment());
            dto.put("date", r.getCreatedAt().format(formatter));
            
            List<String> imageUrls = r.getImages().stream()
                .map(img -> baseUrl + "/" + UPLOAD_DIR + img.getImageUrl())
                .collect(Collectors.toList());
            dto.put("images", imageUrls);
            
            return dto;
        }).collect(Collectors.toList());
    }

    public void addReview(UUID productId, Long userId, Double rating, String comment, List<MultipartFile> files) throws IOException {
        if (reviewRepository.findByProductIdAndUserId(productId, userId).isPresent()) {
            throw new RuntimeException("Bạn đã đánh giá sản phẩm này rồi!");
        }
        
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Review review = Review.builder()
                .product(product)
                .user(user)
                .rating(rating)
                .comment(comment)
                .build();

        if (files != null && !files.isEmpty()) {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    
                    ReviewImage reviewImage = ReviewImage.builder()
                            .imageUrl(fileName)
                            .review(review)
                            .build();
                    review.getImages().add(reviewImage);
                }
            }
        }

        reviewRepository.save(review);
        
        // Cập nhật lại rating tổng của Product
        List<Review> reviews = reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
        double totalRating = 0;
        for (Review rv : reviews) {
            totalRating += rv.getRating();
        }
        double avgRating = reviews.size() > 0 ? totalRating / reviews.size() : 0.0;
        product.setRating(avgRating);
        product.setRatingCount(reviews.size());
        productRepository.save(product);
    }

    public void clearAllReviews() {
        reviewRepository.deleteAll();
    }

    public boolean hasUserReviewedProduct(UUID productId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return reviewRepository.findByProductIdAndUserId(productId, user.getId()).isPresent();
    }
}
