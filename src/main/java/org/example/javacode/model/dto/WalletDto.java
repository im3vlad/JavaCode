package org.example.javacode.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class WalletDto {
    private long id;
    private UUID walletUuid;
    private BigDecimal balance;
}
