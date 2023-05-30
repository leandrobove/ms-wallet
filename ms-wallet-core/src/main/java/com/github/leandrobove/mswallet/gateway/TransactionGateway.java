package com.github.leandrobove.mswallet.gateway;

import com.github.leandrobove.mswallet.entity.Transaction;

public interface TransactionGateway {
    void create(Transaction transaction);
}
