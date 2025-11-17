package repository;

import models.Bet;
import java.util.List;
import java.util.UUID;

public interface BetRepository {
    Bet save(Bet bet);
    List<Bet> findByUserId(UUID userId);
}

