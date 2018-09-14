package org.demon.sdk.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Demon-HY
 */
@Table(name = "user_role")
@Data
@NoArgsConstructor
public class UserRole implements Serializable {

    private static final long serialVersionUID = -1411551573454552438L;
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
}