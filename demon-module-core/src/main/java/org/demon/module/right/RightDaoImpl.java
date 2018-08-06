package org.demon.module.right;

import org.demon.sdk.entity.Right;
import org.demon.starter.autoconfigure.mysql.GenertedJdbcTemplate;
import org.demon.starter.common.jdbc.CommonDao;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class RightDaoImpl extends CommonDaoImpl<Right> {

    @Resource
    private GenertedJdbcTemplate jdbcTemplate;

    private GenertedJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public List<Right> getRights() {
        CommonDao.Criteria criteria = createCriteria();
        return selectByCriteria(criteria, Right.class);
    }
}
