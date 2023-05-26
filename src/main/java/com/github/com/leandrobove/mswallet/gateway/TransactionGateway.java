package com.github.com.leandrobove.mswallet.gateway;

import com.github.com.leandrobove.mswallet.entity.Transaction;

public interface TransactionGateway {
    void create(Transaction client);
}
