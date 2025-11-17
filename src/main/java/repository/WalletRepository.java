package repository;

import models.Wallet;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    void createWallet(UUID userId);
    Optional<Wallet> findByUserId(UUID userId);
    boolean updateBalance(UUID userId, BigDecimal newBalance);
}

