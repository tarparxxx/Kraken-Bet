package services;

import models.Bet;
import org.springframework.stereotype.Service;
import repository.BetRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class BetService {

    private final BetRepository betRepository;
    private final WalletService walletService;
    private final Random random = new Random();

    public BetService(BetRepository betRepository, WalletService walletService) {
        this.betRepository = betRepository;
        this.walletService = walletService;
    }

    public Bet placeBet(UUID userId, BigDecimal amount) {

        boolean ok = walletService.spendForBet(userId, amount);
        if (!ok) return null;

        boolean win = random.nextBoolean();

        if (win) {
            walletService.addWin(userId, amount);
        }

        Bet bet = new Bet(
                UUID.randomUUID(),
                userId,
                amount,
                win,
                LocalDateTime.now()
        );

        return betRepository.save(bet);
    }

    public List<Bet> getHistory(UUID userId) {
        return betRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}


