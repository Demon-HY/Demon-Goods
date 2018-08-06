package org.demon.module.user;

import org.demon.sdk.entity.User;
import org.demon.starter.autoconfigure.mysql.GenertedJdbcTemplate;
import org.demon.starter.common.jdbc.CommonDao;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserDaoImpl extends CommonDaoImpl<User> {

    @Resource
    private GenertedJdbcTemplate genertedJdbcTemplate;

    private GenertedJdbcTemplate getGenertedJdbcTemplate() {
        return genertedJdbcTemplate;
    }

    /**
     * 通过账号查询用户信息
     *
     * @param account 账号
     * @return
     */
    public User findByAccount(String account) {
        CommonDao.Criteria criteria = this.createCriteria();
        criteria.eq("name", account);
        return selectOneByCriteria(criteria, User.class);
    }

    /**
     * 通过手机号查询用户信息
     *
     * @param phone
     * @return
     */
    public User findByPhone(String phone) {
        CommonDao.Criteria criteria = this.createCriteria();
        criteria.eq("phone", phone);
        return selectOneByCriteria(criteria, User.class);
    }
}
