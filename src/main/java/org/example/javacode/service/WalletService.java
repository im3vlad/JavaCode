package org.example.javacode.service;


import org.example.javacode.enums.OperationType;
import org.example.javacode.model.entity.WalletEntity;
import org.example.javacode.repository.WalletRepository;
import org.example.javacode.repository.WalletRepositoryImpl;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@Service
public class WalletService {

    private final WalletRepositoryImpl walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = (WalletRepositoryImpl) walletRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public WalletEntity processTransaction(UUID walletUuid, OperationType operationType, Double amount) throws ChangeSetPersister.NotFoundException {
        Optional<WalletEntity> optionalWallet = walletRepository.findByWalletUuid(walletUuid);
        WalletEntity wallet = optionalWallet.orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal newBalance;

        switch (operationType) {
            case DEPOSIT:
                newBalance = currentBalance.add(BigDecimal.valueOf(amount));
                break;
            case WITHDRAW:
                if (currentBalance.compareTo(BigDecimal.valueOf(amount)) < 0) {
                    throw new ChangeSetPersister.NotFoundException();
                }
                newBalance = currentBalance.subtract(BigDecimal.valueOf(amount));
                break;
            default:
                throw new IllegalArgumentException("Invalid operation type");
        }

        wallet.setBalance(newBalance);
        return walletRepository.save(wallet);
    }

    @Transactional(readOnly = true)
    public Optional<WalletEntity> findByWalletUuid(UUID walletUuid) {
        return walletRepository.findByWalletUuid(walletUuid);
    }
}
