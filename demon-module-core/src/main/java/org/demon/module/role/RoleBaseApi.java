package org.demon.module.role;

import org.demon.sdk.entity.Role;
import org.demon.sdk.environment.Env;
import org.demon.sdk.inner.IRoleBaseApi;
import org.springframework.stereotype.Service;

@Service
public class RoleBaseApi implements IRoleBaseApi {

    @Override
    public Role addRole(Env env, String roleName, String description) {
        return null;
    }

    @Override
    public Role editRole(Env env, Role role) {
        return null;
    }

    @Override
    public boolean removeRole(Env env, Long roleId) {
        return false;
    }
}
