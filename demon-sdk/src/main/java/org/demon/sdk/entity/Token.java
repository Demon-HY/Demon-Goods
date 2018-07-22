package org.demon.sdk.entity;

import org.apache.commons.codec.binary.Base64;
import org.demon.utils.datetime.DateUtils;
import org.demon.utils.datetime.Time;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

/**
 * @author Demon-Coffee
 * @since 1.0
 */
@Table(name = "token")
public class Token implements Serializable {

	private static final long serialVersionUID = 1668574555772364189L;
	// 自增ID
	@Id
	@Column(name = "id")
	public Long id;

	// 用户唯一标识
	@Column(name = "token")
	public String token;

	// 用户ID
	@Column(name = "uid")
	public Long uid;

	// 过期时间
	@Column(name = "expires")
	public Date expires;

	// 访问IP
	@Column(name = "ip")
	public String ip;

	// 登录设备:pc/android/iphone
	@Column(name = "device")
	public String device;

//	/**
//	 * 客户端访问的代理
//	 */
//	@Column(name = "user_agent")
//	public String userAgent;

	// 创建时间
	@Column(name = "create_time")
	public Date createTime;

	// 更新时间
	@Column(name = "update_time")
	public Date updateTime;


	public Token(){}

	public Token(String token, long uid, Date expires, Date createTime, String ip, String device, String userAgent) {
		this.token = token;
		this.uid = uid;
		this.expires = expires;
		this.createTime = createTime;
		this.updateTime = createTime;
		this.ip = ip;
		this.device = device;
		if (userAgent != null && userAgent.length() > 500) {
			userAgent = userAgent.substring(0, 500);
		}
//		this.userAgent = userAgent;
	}

	/**
	 * 新建token信息
	 * @param uid 用户id
	 * @param tokenAge token寿命
	 * @param ip 用户的ip地址
	 * @param device 用户登录设备
	 * @param userAgent 用户访问使用的代理
	 * @return
	 */
	public static Token newToken(long uid, long tokenAge, String ip, String device, String userAgent) {
		Date createTime = new Date();
		long now = createTime.getTime();
		return new Token(makeToken() + "@" + uid, uid, DateUtils.longTimeToDate(now + tokenAge), createTime,
				ip, device, userAgent);
	}

	/**
	 * 生成token字符串
	 * @return
	 */
	private static String makeToken() {
		long uuid = UUID.randomUUID().getMostSignificantBits();
		byte[] uuidBytes = ByteBuffer.allocate(8).putLong(uuid).array();
		return Base64.encodeBase64URLSafeString(uuidBytes);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
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

}