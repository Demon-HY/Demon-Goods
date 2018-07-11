package org.demon.right.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Demon-Coffee
 * @since 1.0
 */
@Table(name = "right")
public class Right implements Serializable {

	private static final long serialVersionUID = 6909648983746981158L;
	// 权限ID
	@Id
	@Column(name = "right_id")
	private Long rightId;

	// 权限名
	@Column(name = "name")
	private String name;

	// 权限类型:0-私有权限,不对外开发,只有超级管理员才有的权限,1-公开权限
	@Column(name = "type")
	private Integer type;

	// 展示使用的名称
	@Column(name = "display_name")
	private String displayName;

	// 权限所属模块
	@Column(name = "module")
	private String module;

	// 预留未知信息,JOSN格式
	@Column(name = "params")
	private String params;


	public Right(){}

	public Long getRightId() {
		return rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}