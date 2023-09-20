package com.financialtransactions.repositories;

import com.financialtransactions.domain.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IFinancialTransactionRepository extends JpaRepository<FinancialTransaction, UUID> {
    @Query(value = "select * from public.financial_transactions where user_sender_id = ?1", nativeQuery = true)
    Optional<FinancialTransaction> findByUserSenderId(UUID id);
    @Query(value = "select * from public.financial_transactions where user_receiver_id = ?1", nativeQuery = true)
    Optional<FinancialTransaction> findByUserReceiverId(UUID id);
    @Query(value = "SELECT * FROM public.financial_transactions WHERE " +
            "financial_transactions.user_sender_id = ?1 OR financial_transactions.user_receiver_id = ?1", nativeQuery = true)
    List<FinancialTransaction> findAllByUserId(UUID userId);
}
