package org.demon.sdk.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Demon-HY
 */
@Table(name = "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 3484841562076354855L;
    // 主键
    @Id
    @Column(name = "id")
    public Long id;

    // 用户ID
    @Column(name = "user_id")
    public Long userId;

    // 角色ID
    @Column(name = "role_id")
    public Long roleId;


    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}