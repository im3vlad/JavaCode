package org.example.javacode.repository;

import org.example.javacode.model.entity.WalletEntity;

import java.util.Optional;
import java.util.UUID;

public class WalletRepositoryImpl implements WalletRepository {

    @Override
    public Optional<WalletEntity> findByWalletUuid(UUID walletUuid) {
        return Optional.empty();
    }

    @Override
    public WalletEntity save(WalletEntity wallet) {
        return null;
    }
}
