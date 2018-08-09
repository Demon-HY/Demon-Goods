package org.demon.sdk.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Demon-HY
 * @since 1.0
 */
@Table(name = "role")
public class Role implements Serializable {

	private static final long serialVersionUID = -6409319496612103638L;
	// 角色ID
	@Id
	@Column(name = "id")
	public Long id;

	// 角色名称
	@Column(name = "name")
	public String name;

	// 是否被锁定:0-正常,1-锁定
	@Column(name = "lock")
	public Integer lock;

	// 角色描述
	@Column(name = "description")
	public String description;

	// 创建时间
	@Column(name = "create_time")
	public Date createTime;

	// 更新时间
	@Column(name = "update_time")
	public Date updateTime;

	/**
	 * 角色拥有的权限
	 */
	public List<Right> rights;


	public Role(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLock() {
		return lock;
	}

	public void setLock(Integer lock) {
		this.lock = lock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<Right> getRights() {
		return rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name='" + name + '\'' +
				", lock=" + lock +
				", description='" + description + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", rights=" + rights +
				'}';
	}
}