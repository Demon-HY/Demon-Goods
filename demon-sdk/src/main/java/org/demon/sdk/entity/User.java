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
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 8390645454224381806L;
	// 用户 Id
	@Id
	@Column(name = "u_id")
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


	public User(){}

	public User(String name, String nick, String phone, String email, String password, String qq) {
		this.name = name;
		this.nick = nick;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.qq = qq;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExattr() {
		return exattr;
	}

	public void setExattr(String exattr) {
		this.exattr = exattr;
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

	public Date getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}

	@Override
	public String toString() {
		return "User{" +
				"uid=" + uid +
				", name='" + name + '\'' +
				", nick='" + nick + '\'' +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", qq='" + qq + '\'' +
				", type=" + type +
				", status=" + status +
				", exattr='" + exattr + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", loadTime=" + loadTime +
				'}';
	}
}