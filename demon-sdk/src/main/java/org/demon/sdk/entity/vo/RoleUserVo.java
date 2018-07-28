package org.demon.sdk.entity.vo;

import org.demon.sdk.entity.Role;
import org.demon.sdk.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * 角色所属用户信息
 */
public class RoleUserVo implements Serializable {

    private static final long serialVersionUID = 2297834288398662420L;
    /**
     * 角色信息
     */
    public Role role;

    /**
     * 用户信息
     */
    public List<User> user;

    public RoleUserVo() {
    }

    public RoleUserVo(Role role, List<User> user) {
        this.role = role;
        this.user = user;
    }

    @Override
    public String toString() {
        return "RoleUserVo{" +
                "role=" + role +
                ", user=" + user +
                '}';
    }
}
