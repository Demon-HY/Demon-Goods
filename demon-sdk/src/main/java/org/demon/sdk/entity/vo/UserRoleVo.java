package org.demon.sdk.entity.vo;

import org.demon.sdk.entity.Role;
import org.demon.sdk.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * 用户所属角色信息
 */
public class UserRoleVo implements Serializable {

    private static final long serialVersionUID = -6580688399180253467L;
    /**
     * 用户信息
     */
    public User user;

    public List<Role> roles;

    public UserRoleVo() {
    }

    public UserRoleVo(User user, List<Role> roles) {
        this.user = user;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserRoleVo{" +
                "user=" + user +
                ", roles=" + roles +
                '}';
    }
}
