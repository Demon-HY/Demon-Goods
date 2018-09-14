package org.demon.sdk.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
public class Role implements Serializable {

    private static final long serialVersionUID = -6634827310239251697L;
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

}