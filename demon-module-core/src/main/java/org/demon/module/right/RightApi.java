package org.demon.module.right;

import org.demon.sdk.entity.Right;
import org.demon.sdk.environment.Env;
import org.demon.sdk.inner.role.IRightApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class RightApi implements IRightApi {

    @Autowired
    private RightDaoImpl rightDao;

    @Override
    public List<Right> getRights(Env env) {
        return rightDao.getRights();
    }

    @Override
    public void setRight(Right right) throws SQLException {
        if (right == null) {
            throw new IllegalArgumentException();
        }

        rightDao.insert(right);
    }

    @Override
    public void deleteRight(Right right) {
        if (right == null) {
            throw new IllegalArgumentException();
        }

        rightDao.removeById(right.rightId, Right.class);
    }
}
