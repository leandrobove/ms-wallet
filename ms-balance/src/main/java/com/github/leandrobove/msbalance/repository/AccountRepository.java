package com.github.leandrobove.msbalance.repository;

import com.github.leandrobove.msbalance.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
