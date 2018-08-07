package org.demon.sdk.entity;

import org.demon.starter.common.jdbc.CommonDaoImpl;

import java.sql.SQLException;

public class Test {

    public static void main(String[] args) throws SQLException {
        CommonDaoImpl<User> userCommonDao = new CommonDaoImpl<>();

        User user = new User();
        user.setUid(100L);

        userCommonDao.insert(user);
    }
}
