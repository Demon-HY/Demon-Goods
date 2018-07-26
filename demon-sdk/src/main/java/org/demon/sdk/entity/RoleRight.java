package org.demon.sdk.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Demon-HY
 * @since 1.0
 */
@Table(name = "role_right")
public class RoleRight implements Serializable {

	private static final long serialVersionUID = 7336582542471014496L;
	// 主键
	@Id
	@Column(name = "id")
	public Integer id;

	// 角色ID
	@Column(name = "role_id")
	public Long roleId;

	// 权限ID
	@Column(name = "right_id")
	public Long rightId;


	public RoleRight(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRightId() {
		return rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	@Override
	public String toString() {
		return "RoleRight{" +
				"id=" + id +
				", roleId=" + roleId +
				", rightId=" + rightId +
				'}';
	}
}