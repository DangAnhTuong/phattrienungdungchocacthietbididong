package com.danganhtuong.app.controller;

import com.danganhtuong.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/google")
    public ResponseEntity<Map<String, String>> googleLogin(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.googleLogin(request));
    }

    @PostMapping("/facebook")
    public ResponseEntity<Map<String, String>> facebookLogin(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.facebookLogin(request));
    }
}