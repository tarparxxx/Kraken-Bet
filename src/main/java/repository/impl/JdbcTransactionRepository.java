package repository.impl;

import database.DatabaseManager;
import models.Transaction;
import models.TransactionType;
import repository.TransactionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcTransactionRepository implements TransactionRepository {

    @Override
    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO transactions (id, user_id, type, amount, created_at) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (transaction.getId() == null) {
                transaction.setId(UUID.randomUUID());
            }

            stmt.setObject(1, transaction.getId());
            stmt.setObject(2, transaction.getUserId());
            stmt.setString(3, transaction.getType().name());
            stmt.setBigDecimal(4, transaction.getAmount());
            stmt.setTimestamp(5, Timestamp.valueOf(transaction.getCreatedAt()));

            stmt.executeUpdate();
            return transaction;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving transaction: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Transaction> findByUserId(UUID userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY created_at DESC";
        List<Transaction> list = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction(
                        rs.getObject("id", UUID.class),
                        rs.getObject("user_id", UUID.class),
                        TransactionType.valueOf(rs.getString("type")),
                        rs.getBigDecimal("amount"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                list.add(t);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding transactions: " + e.getMessage(), e);
        }
    }
}

