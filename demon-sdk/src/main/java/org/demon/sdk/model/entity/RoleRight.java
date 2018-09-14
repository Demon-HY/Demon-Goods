package org.demon.sdk.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Demon-HY
 * @since 1.0
 */
@Table(name = "role_right")
@Data
@NoArgsConstructor
public class RoleRight implements Serializable {

	private static final long serialVersionUID = -2731515037149709185L;
	// 主键
	@Id
	@Column(name = "id")
	public Long id;

	// 角色ID
	@Column(name = "role_id")
	public Long roleId;

	// 权限ID
	@Column(name = "right_id")
	public Long rightId;

	public RoleRight(Long roleId, Long rightId) {
		this.roleId = roleId;
		this.rightId = rightId;
	}
}