package services;

import models.User;
import models.Wallet;
import repository.UserRepository;
import repository.WalletRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class AuthService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public AuthService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public User register(String username, String password) {
        // Проверка: существует ли логин
        Optional<User> existing = userRepository.findByUsername(username);
        if (existing.isPresent()) {
            return null; // username занят
        }

        // Создаём пользователя
        User user = new User(
                UUID.randomUUID(),
                username,
                hashPassword(password),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // Сохраняем в БД
        userRepository.save(user);

        // Создаём кошелёк
        walletRepository.createWallet(user.getId());

        return user;
    }

    public User login(String username, String password) {
        Optional<User> existing = userRepository.findByUsername(username);

        if (existing.isEmpty()) {
            return null; // User not found
        }

        User user = existing.get();

        // Проверяем hash пароля
        if (user.getPasswordHash().equals(hashPassword(password))) {
            return user;
        }

        return null; // wrong password
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // переводим байты в hex строку
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash error: " + e.getMessage(), e);
        }
    }
}

