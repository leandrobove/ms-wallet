package com.github.com.leandrobove.mswallet.event;

import com.github.com.leandrobove.mswallet.usecase.createTransaction.CreateTransactionUseCaseOutputDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionCreatedEvent extends ApplicationEvent {
    private CreateTransactionUseCaseOutputDto payload;

    public TransactionCreatedEvent(Object source, CreateTransactionUseCaseOutputDto payload) {
        super(source);
        this.payload = payload;
    }
}
