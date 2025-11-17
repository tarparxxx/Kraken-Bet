package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Bet {
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private boolean isWin;
    private LocalDateTime createdAt;

    public Bet() {
    }

    public Bet(UUID id, UUID userId, BigDecimal balance, boolean isWin, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.isWin = isWin;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isWin() {return isWin; }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setWin(boolean isWin) {
        this.isWin = isWin;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", userId=" + userId +
                ", balance=" + balance +
                ", isWin=" + isWin +
                ", createdAt=" + createdAt +
                "}";
    }
}
