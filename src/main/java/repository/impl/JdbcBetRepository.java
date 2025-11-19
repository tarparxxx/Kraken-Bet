package repository.impl;

import database.DatabaseManager;
import models.Bet;
import repository.BetRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcBetRepository implements BetRepository {
    @Override
    public Bet save(Bet bet) {
        String sql = "INSERT INTO bets (id, user_id, amount, is_win, created_at)" +
                "VALUES (?, ?, ?, ?, ?)"; // recheck user_id because maybe it's bad, IDK

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (bet.getId() == null) {
                bet.setId(UUID.randomUUID());
            }

            stmt.setObject(1, bet.getId());
            stmt.setObject(2, bet.getUserId());
            stmt.setBigDecimal(3, bet.getAmount());
            stmt.setBoolean(4, bet.isWin());
            stmt.setTimestamp(5, Timestamp.valueOf(bet.getCreatedAt()));

            stmt.executeUpdate();
            return bet;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving bet: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Bet> findByUserId(UUID userId) {
        String sql = "SELECT * FROM bets WHERE user_id = ? ORDER BY created_at DESC";
        List<Bet> bets = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bet bet = new Bet(
                        rs.getObject("id", UUID.class),
                        rs.getObject("user_id", UUID.class),
                        rs.getBigDecimal("amount"),
                        rs.getBoolean("is_win"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                bets.add(bet);
            }

            return bets;

        } catch (SQLException e) {
            throw new RuntimeException("Error loading bets: " + e.getMessage(), e);
        }
    }
}
