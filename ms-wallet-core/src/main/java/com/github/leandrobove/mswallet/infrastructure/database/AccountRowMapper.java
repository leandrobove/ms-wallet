package com.github.leandrobove.mswallet.infrastructure.database;

import com.github.leandrobove.mswallet.domain.account.Account;
import com.github.leandrobove.mswallet.domain.client.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = Client.rebuildClient(
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getObject(9, OffsetDateTime.class),
                rs.getObject(10, OffsetDateTime.class)
        );

        return Account.rebuildAccount(
                rs.getString(1),
                client,
                rs.getBigDecimal(2),
                rs.getObject(3, OffsetDateTime.class),
                rs.getObject(4, OffsetDateTime.class)
        );
    }
}
