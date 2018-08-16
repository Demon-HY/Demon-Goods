package org.demon.sdk.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.demon.sdk.entity.Role;
import org.demon.sdk.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * 角色所属用户信息
 */
@Data
@ApiModel(value = "角色所属用户信息")
public class RoleUserVo implements Serializable {

    private static final long serialVersionUID = 6899946142958935413L;
    /**
     * 角色信息
     */
    @ApiModelProperty(value = "角色信息")
    public Role role;

    /**
     * 用户信息
     */
    @ApiModelProperty(value = "用户信息")
    public List<User> user;

    public RoleUserVo() {
    }

    public RoleUserVo(Role role, List<User> user) {
        this.role = role;
        this.user = user;
    }
}
