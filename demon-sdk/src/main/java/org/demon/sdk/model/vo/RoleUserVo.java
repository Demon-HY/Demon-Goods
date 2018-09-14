package org.demon.sdk.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.demon.sdk.model.entity.Role;
import org.demon.sdk.model.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * 角色所属用户信息
 */
@Data
@NoArgsConstructor
@ApiModel(value = "角色所属用户信息")
public class RoleUserVo implements Serializable {

    private static final long serialVersionUID = -1646629190948285783L;
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

    public RoleUserVo(Role role, List<User> user) {
        this.role = role;
        this.user = user;
    }
}
