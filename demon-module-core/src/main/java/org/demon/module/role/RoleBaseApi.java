package org.demon.module.role;

import org.demon.sdk.entity.Role;
import org.demon.sdk.environment.Env;
import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.inner.IRoleBaseApi;
import org.demon.sdk.utils.RetCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RoleBaseApi implements IRoleBaseApi {

    @Autowired
    private RoleDaoImpl roleDao;

    @Override
    public Role createRole(Env env, Role role) throws Exception {
        if (null == role) {
            throw new IllegalArgumentException();
        }

        Role temp = roleDao.getRole(role.name);
        if (temp != null) {
            throw new LogicalException(RetCodeEnum.ERR_ROLE_EXIST);
        }

        roleDao.addRole(role);

        return null;
    }

    private void addRole(Env env, Role role) {

    }

    @Override
    public Role editRole(Env env, Role role) throws Exception {
        if (null == role) {
            throw new IllegalArgumentException();
        }

        return null;
    }

    @Override
    public boolean deleteRole(Env env, Long roleId) throws Exception {
        return false;
    }
}
