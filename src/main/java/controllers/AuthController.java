package controllers;

import models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String password = req.get("password");

        User user = authService.register(username, password);

        if (user == null) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String password = req.get("password");

        User user = authService.login(username, password);

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        return ResponseEntity.ok(user);
    }
}

