package services;

import models.Transaction;
import models.TransactionType;
import models.Wallet;
import repository.TransactionRepository;
import repository.WalletRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getBalance(UUID userId) {
        Optional<Wallet> wallet = walletRepository.findByUserId(userId);
        return wallet.map(Wallet::getBalance).orElse(BigDecimal.ZERO);
    }

    public boolean deposit(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false; // сумма должна быть > 0
        }

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        BigDecimal newBalance = wallet.getBalance().add(amount);

        boolean updated = walletRepository.updateBalance(userId, newBalance);

        if (updated) {
            recordTransaction(userId, amount, TransactionType.DEPOSIT);
        }

        return updated;
    }

    public boolean withdraw(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        // Проверяем, хватит ли средств
        if (wallet.getBalance().compareTo(amount) < 0) {
            return false; // недостаточно средств
        }

        BigDecimal newBalance = wallet.getBalance().subtract(amount);

        boolean updated = walletRepository.updateBalance(userId, newBalance);

        if (updated) {
            recordTransaction(userId, amount.negate(), TransactionType.WITHDRAW);
        }

        return updated;
    }

    public boolean spendForBet(UUID userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance().compareTo(amount) < 0) {
            return false;
        }

        BigDecimal newBalance = wallet.getBalance().subtract(amount);
        boolean updated = walletRepository.updateBalance(userId, newBalance);

        if (updated) {
            recordTransaction(userId, amount.negate(), TransactionType.BET);
        }

        return updated;
    }

    public void addWin(UUID userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        BigDecimal newBalance = wallet.getBalance().add(amount);
        walletRepository.updateBalance(userId, newBalance);

        recordTransaction(userId, amount, TransactionType.WIN);
    }

    private void recordTransaction(UUID userId, BigDecimal amount, TransactionType type) {
        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                userId,
                type,
                amount,
                LocalDateTime.now()
        );

        transactionRepository.save(transaction);
    }
}

