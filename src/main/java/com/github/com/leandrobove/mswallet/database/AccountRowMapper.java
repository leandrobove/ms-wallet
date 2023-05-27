package com.github.com.leandrobove.mswallet.database;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.entity.Client;
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
                rs.getObject(8, OffsetDateTime.class),
                rs.getObject(9, OffsetDateTime.class)
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
