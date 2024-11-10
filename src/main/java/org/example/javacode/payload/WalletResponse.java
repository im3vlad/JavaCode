package org.example.javacode.payload;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class WalletResponse {

    private UUID walletUuid;
    private BigDecimal balance;
}
