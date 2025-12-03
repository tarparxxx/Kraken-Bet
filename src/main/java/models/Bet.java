package models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bets")
public class Bet {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "is_win", nullable = false)
    private boolean isWin;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Bet() {
    }

    public Bet(UUID id, UUID userId, BigDecimal amount, boolean isWin, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.isWin = isWin;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
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

    public void setAmount(BigDecimal balance) {
        this.amount = amount;
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
                ", amount=" + amount +
                ", isWin=" + isWin +
                ", createdAt=" + createdAt +
                "}";
    }
}
