package controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.WalletService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<?> getBalance(@PathVariable UUID userId) {
        return ResponseEntity.ok(walletService.getBalance(userId));
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Map<String, String> req) {
        UUID userId = UUID.fromString(req.get("userId"));
        BigDecimal amount = new BigDecimal(req.get("amount"));

        boolean ok = walletService.deposit(userId, amount);
        return ResponseEntity.ok(Map.of("success", ok));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody Map<String, String> req) {
        UUID userId = UUID.fromString(req.get("userId"));
        BigDecimal amount = new BigDecimal(req.get("amount"));

        boolean ok = walletService.withdraw(userId, amount);
        return ResponseEntity.ok(Map.of("success", ok));
    }
}

