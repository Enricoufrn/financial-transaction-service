package com.financialtransactions.repositories;

import com.financialtransactions.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IAccountRepository extends JpaRepository<Account, UUID> {
    @Query(value = "SELECT * FROM public.accounts WHERE accounts.user_id = :userId AND accounts.active = TRUE", nativeQuery = true)
    Optional<Account> findByUserId(@Param("userId") UUID userId);
    @Query(value = "SELECT * FROM public.accounts WHERE accounts.number = ?1", nativeQuery = true)
    Optional<Account> findByNumber(String number);
    @Query(value = "SELECT (number) FROM public.accounts WHERE accounts.user_id = ?1", nativeQuery = true)
    Optional<String> findNumberByUserId(UUID userId);
    @Override
    @Query(value = "SELECT * FROM public.accounts WHERE accounts.active = true", nativeQuery = true)
    List<Account> findAll();
}
