package com.demon.user.dao;

import com.demon.user.entity.User;
import com.demon.web.common.jdbc.CommonDao;
import com.demon.web.common.jdbc.CommonDaoImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserDaoImpl extends CommonDaoImpl<User> {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 通过账号查询用户信息
     * @param account 账号
     * @return
     */
    public User findByAccount(String account) {
        CommonDao.Criteria criteria = this.createCriteria();
        criteria.eq("account", account);
        return selectOneByCriteria(criteria, User.class);
    }
}
