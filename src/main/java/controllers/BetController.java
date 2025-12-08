package controllers;

import models.Bet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.BetService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/bets")
public class BetController {

    private final BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeBet(@RequestBody Map<String, String> req) {
        UUID userId = UUID.fromString(req.get("userId"));
        BigDecimal amount = new BigDecimal(req.get("amount"));

        Bet bet = betService.placeBet(userId, amount);

        if (bet == null) {
            return ResponseEntity.badRequest().body("Not enough money");
        }

        return ResponseEntity.ok(bet);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Bet>> getHistory(@PathVariable UUID userId) {
        return ResponseEntity.ok(betService.getHistory(userId));
    }
}

