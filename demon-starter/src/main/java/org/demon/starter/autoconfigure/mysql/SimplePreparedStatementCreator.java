package org.demon.starter.autoconfigure.mysql;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SimplePreparedStatementCreator implements PreparedStatementCreator, SqlProvider {

    private final String sql;

    public SimplePreparedStatementCreator(String sql) {
        Assert.notNull(sql, "SQL must not be null");
        this.sql = sql;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        // Statement.RETURN_GENERATED_KEYS 不加这个是获取不到自增ID的
        return con.prepareStatement(this.sql, Statement.RETURN_GENERATED_KEYS);
    }

    @Override
    public String getSql() {
        return this.sql;
    }
}
