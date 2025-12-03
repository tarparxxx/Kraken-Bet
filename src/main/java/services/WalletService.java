package services;

import models.Transaction;
import models.TransactionType;
import models.Wallet;
import org.springframework.stereotype.Service;
import repository.TransactionRepository;
import repository.WalletRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getBalance(UUID userId) {
        return walletRepository.findByUserId(userId)
                .map(Wallet::getBalance)
                .orElse(BigDecimal.ZERO);
    }

    public boolean deposit(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) return false;

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        logTransaction(userId, amount, TransactionType.DEPOSIT);

        return true;
    }

    public boolean withdraw(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) return false;

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance().compareTo(amount) < 0) return false;

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        logTransaction(userId, amount.negate(), TransactionType.WITHDRAW);

        return true;
    }

    public boolean spendForBet(UUID userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance().compareTo(amount) < 0) return false;

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        logTransaction(userId, amount.negate(), TransactionType.BET);

        return true;
    }

    public void addWin(UUID userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        logTransaction(userId, amount, TransactionType.WIN);
    }

    private void logTransaction(UUID userId, BigDecimal amount, TransactionType type) {
        Transaction t = new Transaction(
                UUID.randomUUID(),
                userId,
                type,
                amount,
                LocalDateTime.now()
        );

        transactionRepository.save(t);
    }
}


