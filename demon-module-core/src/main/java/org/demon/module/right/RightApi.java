package org.demon.module.right;

import org.demon.module.role.RoleDaoImpl;
import org.demon.sdk.model.entity.Right;
import org.demon.sdk.model.entity.Role;
import org.demon.sdk.environment.Env;
import org.demon.sdk.exception.LogicalException;
import org.demon.sdk.inner.role.IRightApi;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.utils.ValidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class RightApi implements IRightApi {

    @Autowired
    private RightDaoImpl rightDao;
    @Autowired
    private RoleDaoImpl roleDao;
    @Autowired
    private RoleRightDaoImpl roleRightDao;
    @Autowired
    private RightUtils rightUtils;

    @Override
    public List<Right> getRights(Env env) throws Exception {
        // 校验权限
        rightUtils.checkRight(env, RightConfigAbstract.MODULE_NAME, RightConfigAbstract.RIGHT_CHECK_RIGHT.getValue0());

        return rightDao.getRights();
    }

    @Override
    public List<Right> getRights(Env env, String moduleName) throws Exception {
        // 校验权限
        rightUtils.checkRight(env, RightConfigAbstract.MODULE_NAME, RightConfigAbstract.RIGHT_CHECK_RIGHT.getValue0());

        return rightDao.getRights(moduleName);
    }

    @Override
    public List<Right> getRoleRights(Env env, Long roleId) throws Exception {
        if (roleId == null) {
            throw new IllegalArgumentException();
        }

        // 校验权限
        rightUtils.checkRight(env, RightConfigAbstract.MODULE_NAME, RightConfigAbstract.RIGHT_CHECK_ROLE_RIGHT.getValue0());

        return rightDao.getRoleRights(roleId);
    }

    @Override
    public void createRight(Env env, Right right) throws Exception {
        if (right == null) {
            throw new IllegalArgumentException();
        }

        // 校验权限
        rightUtils.checkRight(env, RightConfigAbstract.MODULE_NAME, RightConfigAbstract.RIGHT_CREATE_RIGHT.getValue0());

        rightDao.insert(right);
    }

    @Override
    public void deleteRight(Env env, Right right) throws Exception {
        if (right == null) {
            throw new IllegalArgumentException();
        }

        // 校验权限
        rightUtils.checkRight(env, RightConfigAbstract.MODULE_NAME, RightConfigAbstract.RIGHT_DELETE_RIGHT.getValue0());

        rightDao.removeById(right.rightId, Right.class);
    }

    @Override
    @Transactional
    public boolean setRoleRight(Env env, Long roleId, List<Right> rights) throws Exception {
        if (ValidUtils.isBlank(roleId) || ValidUtils.isBlank(rights)) {
            throw new IllegalArgumentException();
        }

        Role role = roleDao.getRole(roleId);
        if (role == null) {
            throw new LogicalException(RetCodeEnum.ERR_ROLE_NOT_FOUND);
        }

        // 获取旧的权限
        List<Right> oldRights = getRoleRights(env, roleId);
        // 需要删除的权限
        List<Right> removeRights = new LinkedList<>();
        // 需要新增的权限
        List<Right> addRights = new LinkedList<>();

        // 校验旧的权限是否需要删除
        checkId(oldRights, rights, removeRights, addRights);


        for (Right addRight : addRights) {
            roleRightDao.setRoleRight(roleId, addRight);
        }

        return false;
    }

    @Override
    public void checkId(List<Right> oldRights, List<Right> rights, List<Right> removeRights, List<Right> addRights) {
        for (Right oldR : oldRights) {
            boolean in = false;
            for (Right r : rights) {
                if (oldR.name.equals(r.name)) {
                    in = true;
                    break;
                }
            }
            if (!in) {
                removeRights.add(oldR);
            }
        }

        // 检查旧权限里面没有的权限,放到新增的权限列表中
        if (oldRights.size() == 0) {
            addRights.addAll(rights);
            return;
        }
        for (Right r : rights) {

            boolean flag = true; // 是否需要新增权限

            for (Right oldR : oldRights) {
                if (r.name.equals(oldR.name)) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                // 旧权限里面没有该权限
                addRights.add(r);
            }
        }
    }

    @Override
    public Right getRight(Env env, String rightName) throws Exception {
        if (ValidUtils.isBlank(rightName)) {
            throw new IllegalArgumentException();
        }

        return rightDao.getRight(rightName);
    }
}
