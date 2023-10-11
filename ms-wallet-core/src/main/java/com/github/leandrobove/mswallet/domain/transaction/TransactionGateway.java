package com.github.leandrobove.mswallet.domain.transaction;

public interface TransactionGateway {
    void create(Transaction transaction);
}
