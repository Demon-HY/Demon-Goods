package org.demon.starter.autoconfigure.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;

/**
 * 封装 JdbcTemplate
 */
public class GenertedJdbcTemplate extends JdbcTemplate {


    public GenertedJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 插入数据后,获取插入数据的自增ID
     * @param sql
     * @param args
     * @return
     * @throws DataAccessException
     */
    public int updateGenerated(String sql, Object... args) throws DataAccessException {
        SimplePreparedStatementCreator psc = new SimplePreparedStatementCreator(sql);
        PreparedStatementSetter pss = newArgPreparedStatementSetter(args);

        logger.debug("Executing prepared SQL update");
        return execute(psc, ps -> {
            try {
                if (pss != null) {
                    pss.setValues(ps);
                }
                int rows = ps.executeUpdate();
                if (logger.isDebugEnabled()) {
                    logger.debug("SQL update affected " + rows + " rows");
                }
                if (rows == 1) {
                    ResultSet set = ps.getGeneratedKeys();
                    if (set.next()) {
                        return set.getInt(1);
                    }
                }

                return null;
            }
            finally {
                if (pss instanceof ParameterDisposer) {
                    ((ParameterDisposer) pss).cleanupParameters();
                }
            }
        });
    }
}
