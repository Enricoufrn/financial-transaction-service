package com.financialtransactions.repositories;

import com.financialtransactions.domain.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface IFinancialTransactionRepository extends JpaRepository<FinancialTransaction, UUID> {
    @Query(value = "select * from public.financial_transactions where user_sender_id = ?1", nativeQuery = true)
    Optional<FinancialTransaction> findByUserSenderId(UUID id);
    @Query(value = "select * from public.financial_transactions where user_receiver_id = ?1", nativeQuery = true)
    Optional<FinancialTransaction> findByUserReceiverId(UUID id);
}
