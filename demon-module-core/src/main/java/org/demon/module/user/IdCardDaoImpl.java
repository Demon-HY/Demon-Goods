package org.demon.module.user;

import org.demon.sdk.entity.IdCard;
import org.demon.starter.autoconfigure.mysql.GenertedJdbcTemplate;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class IdCardDaoImpl extends CommonDaoImpl<IdCard> {

    @Resource
    private GenertedJdbcTemplate jdbcTemplate;

    private GenertedJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
