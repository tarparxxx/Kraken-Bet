package repository;

import models.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BetRepository extends JpaRepository<Bet, UUID> {

    List<Bet> findByUserIdOrderByCreatedAtDesc(UUID userId);
}


