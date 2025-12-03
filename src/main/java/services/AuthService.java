package services;

import models.User;
import repository.UserRepository;
import repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public AuthService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public User register(String username, String password) {
        Optional<User> existing = userRepository.findByUsername(username);
        if (existing.isPresent()) {
            return null;
        }

        User user = new User(
                UUID.randomUUID(),
                username,
                hash(password),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        userRepository.save(user);

        walletRepository.save(new models.Wallet(user.getId(), java.math.BigDecimal.ZERO));

        return user;
    }

    public User login(String username, String password) {
        Optional<User> existing = userRepository.findByUsername(username);

        if (existing.isEmpty()) {
            return null;
        }

        User u = existing.get();
        return u.getPasswordHash().equals(hash(password)) ? u : null;
    }

    private String hash(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] arr = digest.digest(text.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : arr) sb.append(String.format("%02x", b));

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Hash error: " + e.getMessage(), e);
        }
    }
}


