package com.ongtangco.trueshot.repository;

import com.ongtangco.trueshot.entity.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDataRepository extends JpaRepository<AccountData, Integer> {
    Optional<AccountData> findByUsernameIgnoreCase(String username);
    Optional<AccountData> findByEmailIgnoreCase(String email);
}
