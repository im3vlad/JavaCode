package org.example.javacode.controller;


import lombok.RequiredArgsConstructor;
import org.example.javacode.model.entity.WalletEntity;
import org.example.javacode.payload.DepositWithdrawRequest;
import org.example.javacode.payload.WalletResponse;
import org.example.javacode.service.Valid;
import org.example.javacode.service.WalletService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/{walletUuid}")
    public ResponseEntity<WalletResponse> processTransaction(
            @PathVariable UUID walletUuid,
            @Valid @RequestBody DepositWithdrawRequest request,
            BindingResult bindingResult
    ) throws ValidationException, ChangeSetPersister.NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldError().getDefaultMessage());
        }

        try {
            WalletEntity updatedWallet = walletService.processTransaction(walletUuid, request.getOperationType(), request.getAmount());
            return new ResponseEntity<>(new WalletResponse(updatedWallet.getWalletUuid(), updatedWallet.getBalance()), HttpStatus.OK);

        } catch (Exception e) {
        }

        @GetMapping("/{walletUuid}")
        public ResponseEntity<WalletResponse> getWallet (@PathVariable UUID walletUuid){
            WalletEntity wallet = walletService.findByWalletUuid(walletUuid)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
            return new ResponseEntity<>(new WalletResponse(wallet.getWalletUuid(), wallet.getBalance()), HttpStatus.OK);
        }
    }
}

