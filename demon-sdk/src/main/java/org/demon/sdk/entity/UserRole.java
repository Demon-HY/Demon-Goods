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
    public Integer id;

    // 用户ID
    @Column(name = "user_id")
    public Integer userId;

    // 角色ID
    @Column(name = "role_id")
    public Integer roleId;


    public UserRole() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
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