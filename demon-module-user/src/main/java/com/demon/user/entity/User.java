package com.demon.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Demon-Coffee
 * @since 1.0
 */
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = -1L;

	// 用户 Id
	@Id
	@Column(name = "uid")
	private Long uid;

	// 用户名:手机号/邮箱
	@Column(name = "name")
	private String name;

	// 昵称
	@Column(name = "nick")
	private String nick;

	// 手机号
	@Column(name = "phone")
	private Integer phone;

	// 邮箱
	@Column(name = "email")
	private String email;

	// 密码
	@Column(name = "password")
	private String password;

	// QQ 号
	@Column(name = "qq")
	private String qq;

	// 用户类型:1-普通用户,9-超级管理员
	@Column(name = "type")
	private Integer type;

	// 用户状态:1-正常,2-锁定,3-删除,4-未实名
	@Column(name = "status")
	private Integer status;

	// 扩展属性集合,JSON格式
	@Column(name = "exattr")
	private String exattr;

	// 创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time")
	private Date createTime;

	// 更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_time")
	private Date updateTime;

	// 最后一次登录时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "load_time")
	private Date loadTime;


	public User(){}

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

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
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

}