package com.github.leandrobove.mswallet.application.gateway;

import com.github.leandrobove.mswallet.domain.entity.Transaction;

public interface TransactionGateway {
    void create(Transaction transaction);
}
