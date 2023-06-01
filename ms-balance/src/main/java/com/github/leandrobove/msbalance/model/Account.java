package com.github.leandrobove.msbalance.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString

@Document(collation = "accounts")
public class Account {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private BigDecimal balance;
}
