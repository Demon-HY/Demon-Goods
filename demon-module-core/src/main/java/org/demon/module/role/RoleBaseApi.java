package org.demon.module.role;

import org.demon.sdk.entity.Role;
import org.demon.sdk.environment.Env;
import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.inner.IRoleBaseApi;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.utils.ValidUtils;
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

        return role;
    }

    @Override
    public Role editRole(Env env, Role role) {
        if (null == role) {
            throw new IllegalArgumentException();
        }

        roleDao.update(role);

        return role;
    }

    @Override
    public boolean deleteRole(Env env, Long roleId) {
        if (ValidUtils.isBlank(roleId)) {
            throw new IllegalArgumentException();
        }

        return roleDao.removeById(roleId, Role.class) == 1;
    }
}
