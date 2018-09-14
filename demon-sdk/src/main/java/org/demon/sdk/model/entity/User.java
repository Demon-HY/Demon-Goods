package org.demon.sdk.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Demon-HY
 * @since 1.0
 */
@Table(name = "user")
@Data
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1218648474415367268L;
    // 用户 Id
	@Id
	@Column(name = "uid")
	public Long uid;

	// 用户名:手机号/邮箱
	@Column(name = "name")
	public String name;

	// 昵称
	@Column(name = "nick")
	public String nick;

	// 手机号
	@Column(name = "phone")
	public String phone;

	// 邮箱
	@Column(name = "email")
	public String email;

	// 密码
	@Column(name = "password")
	public String password;

	// QQ 号
	@Column(name = "qq")
	public String qq;

	// 用户类型:1-普通用户,9-超级管理员
	@Column(name = "type")
	public Integer type;

	// 用户状态:1-正常,2-锁定,3-删除,4-未实名
	@Column(name = "status")
	public Integer status;

	// 扩展属性集合,JSON格式
	@Column(name = "exattr")
	public String exattr;

	// 创建时间
	@Column(name = "create_time")
	public Date createTime;

	// 更新时间
	@Column(name = "update_time")
	public Date updateTime;

	// 最后一次登录时间
	@Column(name = "load_time")
	public Date loadTime;

	public User(String name, String nick, String phone, String email, String password, String qq) {
		this.name = name;
		this.nick = nick;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.qq = qq;
	}
}