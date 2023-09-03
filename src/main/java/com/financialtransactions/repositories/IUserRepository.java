package com.financialtransactions.repositories;

import com.financialtransactions.domain.User;
import com.financialtransactions.exceptions.ResourceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT * FROM public.users WHERE users.login = ?1", nativeQuery = true)
    Optional<User> findByLogin(String login);
    @Query(value = "SELECT * FROM public.users WHERE users.email = ?1", nativeQuery = true)
    Optional<User> findByEmail(String email);
    @Query(value = "SELECT * FROM public.users WHERE users.document = ?1", nativeQuery = true)
    Optional<User> findByDocument(String document);
    @Override
    @Query(value = "SELECT * FROM public.users WHERE users.active = true", nativeQuery = true)
    List<User> findAll();
}
