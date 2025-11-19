package repository.impl;

import database.DatabaseManager;
import models.Wallet;
import repository.WalletRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class JdbcWalletRepository implements WalletRepository {

    @Override
    public void createWallet(UUID userId) {
        String sql = "INSERT INTO wallet (user_id, balance) VALUES (?, 0)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error creating wallet: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Wallet> findByUserId(UUID userId) {
        String sql = "SELECT * FROM wallet WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Wallet wallet = new Wallet(
                        rs.getObject("user_id", UUID.class),
                        rs.getBigDecimal("balance")
                );
                return Optional.of(wallet);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding wallet: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateBalance(UUID userId, BigDecimal newBalance) {
        String sql = "UPDATE wallet SET balance = ? WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, newBalance);
            stmt.setObject(2, userId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating balance: " + e.getMessage(), e);
        }
    }
}

