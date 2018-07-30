package org.demon.module.right;

import org.demon.sdk.entity.Right;
import org.demon.sdk.environment.Env;
import org.demon.sdk.inner.IRightApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RightApi implements IRightApi {

    @Autowired
    private RightDaoImpl rightDao;

    @Override
    public List<Right> getRights(Env env) {
//        CommonDao.Criteria criteria = rightDao.createCriteria();
        return rightDao.selectByCriteria(null, Right.class);
    }
}
