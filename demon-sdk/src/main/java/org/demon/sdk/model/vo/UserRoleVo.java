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
 * 用户所属角色信息
 */
@Data
@NoArgsConstructor
@ApiModel(value = "用户所属角色信息")
public class UserRoleVo implements Serializable {

    private static final long serialVersionUID = -1761870292252677123L;
    /**
     * 用户信息
     */
    @ApiModelProperty(value = "用户信息")
    public User user;

    @ApiModelProperty(value = "用户所属角色")
    public List<Role> roles;

    public UserRoleVo(User user, List<Role> roles) {
        this.user = user;
        this.roles = roles;
    }
}
