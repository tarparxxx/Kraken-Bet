package controllers;

import models.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.TransactionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable UUID userId) {
        return ResponseEntity.ok(transactionService.getUserTransactions(userId));
    }
}

