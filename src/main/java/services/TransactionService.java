package services;

import models.Transaction;
import repository.TransactionRepository;

import java.util.List;
import java.util.UUID;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * История транзакций пользователя (пополнения, выводы, ставки, выигрыши)
     */
    public List<Transaction> getUserTransactions(UUID userId) {
        return transactionRepository.findByUserId(userId);
    }
}

