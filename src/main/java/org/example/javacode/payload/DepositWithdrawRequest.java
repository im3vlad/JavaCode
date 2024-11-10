package org.example.javacode.payload;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.javacode.enums.OperationType;

@Data
@AllArgsConstructor
public class DepositWithdrawRequest {

    private OperationType operationType;
    private Double amount;
}
