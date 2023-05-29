package com.github.com.leandrobove.mswallet.event;

import com.github.com.leandrobove.mswallet.usecase.createTransaction.CreateTransactionUseCaseOutputDto;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class TransactionCreatedEvent extends BaseEvent implements Serializable {
    private CreateTransactionUseCaseOutputDto payload;

    public TransactionCreatedEvent(CreateTransactionUseCaseOutputDto payload) {
        super("TransactionCreated");
        this.payload = payload;
    }
}
