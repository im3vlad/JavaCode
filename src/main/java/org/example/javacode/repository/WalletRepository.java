package org.example.javacode.repository;

import org.example.javacode.model.entity.WalletEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository {
    Optional<WalletEntity> findByWalletUuid(UUID walletUuid);

    WalletEntity save(WalletEntity wallet);

}
