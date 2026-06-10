package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.User;
import com.danganhtuong.app.repository.UserRepository;
import com.danganhtuong.app.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;

    @Value("${app.google.client-id:}")
    private String googleClientId;

    public Map<String, String> register(Map<String, String> request) {
        if (userRepository.findByEmail(request.get("email")).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng!");
        }

        User user = User.builder()
                .name(request.get("name"))
                .email(request.get("email"))
                .password(passwordEncoder.encode(request.get("password")))
                .provider("LOCAL")
                .build();

        userRepository.save(user);
        String jwt = tokenProvider.generateToken(user);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return response;
    }

    public Map<String, String> login(Map<String, String> request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.get("email"), request.get("password"))
        );

        User user = (User) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(user);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return response;
    }

    public Map<String, String> googleLogin(Map<String, String> request) {
        try {
            String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + request.get("token");
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> payload = response.getBody();
                
                // Verify Client ID if configured
                String aud = (String) payload.get("aud");
                if (googleClientId != null && !googleClientId.isEmpty() && !googleClientId.equals("YOUR_GOOGLE_CLIENT_ID_HERE")) {
                    if (!googleClientId.equals(aud)) {
                        throw new RuntimeException("Token không dành cho ứng dụng này!");
                    }
                }

                String email = (String) payload.get("email");
                String name = (String) payload.get("name");
                String providerId = (String) payload.get("sub");
                
                if (email == null || email.isEmpty()) {
                    email = providerId + "@google.local"; // Fallback email if null
                }

                return processSocialLogin(email, name, providerId, "GOOGLE", request.get("action"));
            } else {
                throw new RuntimeException("Google Token không hợp lệ!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi xác thực Google Token: " + e.getMessage());
        }
    }

    public Map<String, String> facebookLogin(Map<String, String> request) {
        try {
            String url = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + request.get("token");
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> payload = response.getBody();
                
                String providerId = (String) payload.get("id");
                String name = (String) payload.get("name");
                String email = (String) payload.get("email");
                
                // Facebook users might not have an email
                if (email == null || email.isEmpty()) {
                    email = providerId + "@facebook.local";
                }

                return processSocialLogin(email, name, providerId, "FACEBOOK", request.get("action"));
            } else {
                throw new RuntimeException("Facebook Token không hợp lệ!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi xác thực Facebook Token: " + e.getMessage());
        }
    }

    private Map<String, String> processSocialLogin(String email, String name, String providerId, String provider, String action) {
        boolean userExists = userRepository.findByEmail(email).isPresent();

        if ("LOGIN".equalsIgnoreCase(action) && !userExists) {
            throw new RuntimeException("Tài khoản chưa được đăng ký! Vui lòng đăng ký trước.");
        }

        if ("REGISTER".equalsIgnoreCase(action) && userExists) {
            throw new RuntimeException("Tài khoản đã tồn tại! Vui lòng đăng nhập.");
        }

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .email(email)
                    .name(name)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            return userRepository.save(newUser);
        });

        String jwt = tokenProvider.generateToken(user);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return response;
    }

    // --- CHỨC NĂNG ĐỔI MẬT KHẨU MỚI THÊM VÀO ---
    public void changePassword(Map<String, String> request) {
        // Lấy email của người dùng ĐANG ĐĂNG NHẬP từ Token
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));

        // Nếu là tài khoản Google/Facebook thì không cho đổi mật khẩu
        if (user.getPassword() == null) {
            throw new RuntimeException("Tài khoản liên kết Google/Facebook không thể đổi mật khẩu theo cách này!");
        }

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(request.get("oldPassword"), user.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không chính xác!");
        }

        // Mã hóa và lưu mật khẩu mới
        user.setPassword(passwordEncoder.encode(request.get("newPassword")));
        userRepository.save(user);
    }
}