package com.github.leandrobove.mswallet.application.usecase.createTransaction;

import com.github.leandrobove.mswallet.domain.entity.Transaction;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class CreateTransactionUseCaseOutput {
    private String transactionId;
    private String accountIdFrom;
    private String accountIdTo;
    private BigDecimal amount;

    public static CreateTransactionUseCaseOutput from(final Transaction transaction) {
        return CreateTransactionUseCaseOutput.builder()
                .transactionId(transaction.getId().value())
                .accountIdFrom(transaction.getAccountFrom().getId().value())
                .accountIdTo(transaction.getAccountTo().getId().value())
                .amount(transaction.getAmount().value())
                .build();
    }
}
