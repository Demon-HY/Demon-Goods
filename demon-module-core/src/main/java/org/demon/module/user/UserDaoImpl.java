package org.demon.module.user;

import org.apache.poi.ss.formula.functions.T;
import org.demon.sdk.entity.RoleRight;
import org.demon.sdk.entity.User;
import org.demon.starter.autoconfigure.mysql.GenertedJdbcTemplate;
import org.demon.starter.common.jdbc.CommonDao;
import org.demon.starter.common.jdbc.CommonDaoImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;

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
    public User findByAccount(String account) throws SQLException {
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
    public User findByPhone(String phone) throws SQLException {
        CommonDao.Criteria criteria = this.createCriteria();
        criteria.eq("phone", phone);
        return selectOneByCriteria(criteria, User.class);
    }

    static class Test extends CommonDaoImpl<RoleRight> {
        public static void main(String[] args) throws IllegalAccessException {
            Test test = new Test();
            RoleRight roleRight = new RoleRight();
            test.test(roleRight);
        }
    }
}
