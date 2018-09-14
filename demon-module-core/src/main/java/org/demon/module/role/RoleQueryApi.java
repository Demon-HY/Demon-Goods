package org.demon.module.role;

import org.apache.commons.lang3.StringUtils;
import org.demon.module.right.RightUtils;
import org.demon.sdk.entity.Role;
import org.demon.sdk.entity.User;
import org.demon.sdk.entity.vo.RoleUserVo;
import org.demon.sdk.entity.vo.UserRoleVo;
import org.demon.sdk.environment.Env;
import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.inner.role.IRightApi;
import org.demon.sdk.inner.role.IRoleQueryApi;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleQueryApi implements IRoleQueryApi {

    @Autowired
    private RoleDaoImpl roleDao;
    @Autowired
    private RightUtils rightUtils;
    @Autowired
    private IRightApi rightApi;


    @Override
    public List<Role> getRoles(Env env) throws Exception {
        // 验证是否有查询所有角色的权限
        rightUtils.checkRight(env, RoleConfigAbstract.MODULE_NAME, RoleConfigAbstract.RIGHT_CHECK_ROLE.getValue0());

        return roleDao.getRoles();
    }

    @Override
    public Role getRole(Env env, String roleName) throws Exception {
        if (ValidUtils.isBlank(roleName)) {
            throw new IllegalAccessException();
        }

        // 验证是否有查看角色权限
        rightUtils.checkRight(env, RoleConfigAbstract.MODULE_NAME, RoleConfigAbstract.RIGHT_CHECK_ROLE.getValue0());

        return roleDao.getRole(roleName);
    }

    @Override
    public Role getRole(Env env, Long roleId) throws Exception {
        if (roleId == null) {
            throw new IllegalAccessException();
        }

        // 验证是否有查看角色权限
        rightUtils.checkRight(env, RoleConfigAbstract.MODULE_NAME, RoleConfigAbstract.RIGHT_CHECK_ROLE.getValue0());

        return roleDao.getRole(roleId);
    }

    @Override
    public UserRoleVo getUserRole(Env env, Long userId) throws Exception {
        if (userId == null) {
            throw new IllegalAccessException();
        }

        // 验证是否有获取用户角色的权限
        rightUtils.checkRight(env, RoleConfigAbstract.MODULE_NAME, RoleConfigAbstract.RIGHT_CHECK_USER_ROLE.getValue0());

        List<Role> roles = roleDao.getUserRoles(userId);

        return new UserRoleVo(env.login.user, roles);
    }

    @Override
    public UserRoleVo getUserRoleRight(Env env, Long userId) throws Exception {
        UserRoleVo userRoleVo = getUserRole(env, userId);
        // 补充角色的权限
        for (Role role : userRoleVo.roles) {
            role.rights = rightApi.getRoleRights(env, role.id);
        }


        return userRoleVo;
    }

    @Override
    public RoleUserVo getRoleUser(Env env, Long roleId) throws Exception {
        if (roleId == null) {
            throw new IllegalAccessException();
        }

        Role role = roleDao.getRole(roleId);
        if (role == null) {
            throw new LogicalException(RetCodeEnum.ERR_ROLE_NOT_FOUND);
        }

        return getRoleUser(env, role);
    }

    @Override
    public RoleUserVo getRoleUser(Env env, String roleName) throws Exception {
        if (StringUtils.isBlank(roleName)) {
            throw new IllegalAccessException();
        }

        Role role = roleDao.getRole(roleName);
        if (role == null) {
            throw new LogicalException(RetCodeEnum.ERR_ROLE_NOT_FOUND);
        }

        return getRoleUser(env, role);
    }

    @Override
    public RoleUserVo getRoleUser(Env env, Role role) throws Exception {
        if (role == null) {
            throw new LogicalException(RetCodeEnum.ERR_ROLE_NOT_FOUND);
        }

        rightUtils.checkRight(env, RoleConfigAbstract.MODULE_NAME, RoleConfigAbstract.RIGHT_CHECK_ROLE_USER.getValue0());

        List<User> users = roleDao.getRoleUsers(role);
        return new RoleUserVo(role, users);
    }
}
