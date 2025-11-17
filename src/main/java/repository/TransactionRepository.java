package repository;

import models.Transaction;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findByUserId(UUID userId);
}

