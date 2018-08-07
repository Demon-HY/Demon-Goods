package org.demon.module.role;

import org.demon.module.right.RightUtils;
import org.demon.sdk.entity.Role;
import org.demon.sdk.environment.Env;
import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.inner.role.IRoleBaseApi;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        // 验证权限
        RightUtils.checkRight(env, RoleConfig.MODULE_NAME, RoleConfig.RIGHT_CREATE_ROLE.getValue0());

        roleDao.addRole(role);

        return role;
    }

    @Override
    public Role editRole(Env env, Role role) {
        if (null == role) {
            throw new IllegalArgumentException();
        }

        // 验证权限
        RightUtils.checkRight(env, RoleConfig.MODULE_NAME, RoleConfig.RIGHT_EDIT_ROLE.getValue0());

        roleDao.update(role);

        return role;
    }

    @Override
    public boolean deleteRole(Env env, Long roleId) {
        if (ValidUtils.isBlank(roleId)) {
            throw new IllegalArgumentException();
        }

        // 验证权限
        RightUtils.checkRight(env, RoleConfig.MODULE_NAME, RoleConfig.RIGHT_DELETE_ROLE.getValue0());

        return roleDao.removeById(roleId, Role.class) == 1;
    }
}
