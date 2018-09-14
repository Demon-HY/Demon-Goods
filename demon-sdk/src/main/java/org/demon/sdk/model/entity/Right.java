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
@Table(name = "right")
@Data
@NoArgsConstructor
public class Right implements Serializable {

	private static final long serialVersionUID = -2354626425337960203L;
	// 权限ID
	@Id
	@Column(name = "right_id")
	public Long rightId;

	// 权限名
	@Column(name = "name")
	public String name;

	// 权限类型:0-私有权限,不对外开发,只有超级管理员才有的权限,1-公开权限
	@Column(name = "type")
	public Integer type;

	// 展示使用的名称
	@Column(name = "display_name")
	public String displayName;

	// 权限所属模块
	@Column(name = "module")
	public String module;

	// 预留未知信息,JOSN格式
	@Column(name = "params")
	public String params;

	public Right(String name, Integer type, String displayName, String module) {
		this.name = name;
		this.type = type;
		this.displayName = displayName;
		this.module = module;
	}

}