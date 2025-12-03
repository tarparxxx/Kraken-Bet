package services;

import models.Bet;
import repository.BetRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BetService {

    private final BetRepository betRepository;
    private final WalletService walletService;
    private final Random random = new Random();

    public BetService(BetRepository betRepository, WalletService walletService) {
        this.betRepository = betRepository;
        this.walletService = walletService;
    }

    public Bet placeBet(UUID userId, BigDecimal amount) {
        // 1. Проверяем баланс / списываем деньги
        boolean ok = walletService.spendForBet(userId, amount);
        if (!ok) {
            return null; // недостаточно средств
        }

        // 2. Генерируем результат игры (50/50)
        boolean isWin = random.nextBoolean();

        // 3. Начисляем выигрыш
        if (isWin) {
            walletService.addWin(userId, amount); // выигрыш = 1:1
        }

        // 4. Создаём запись ставки
        Bet bet = new Bet(
                UUID.randomUUID(),
                userId,
                amount,
                isWin,
                LocalDateTime.now()
        );

        // 5. Сохраняем в БД
        return betRepository.save(bet);
    }

    public List<Bet> getHistory(UUID userId) {
        return betRepository.findByUserId(userId);
    }
}

