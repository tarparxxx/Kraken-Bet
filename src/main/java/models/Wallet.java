package models;

import java.math.BigDecimal;
import java.util.UUID;

public class Wallet {
    private UUID userId;
    private BigDecimal balance;

    public Wallet() {
    }

    public Wallet(UUID userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "userId=" + userId +
                ", balance=" + balance +
                '}';
    }
}
