package org.demon.sdk.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Demon-HY
 * @since 1.0
 */
@Data
@Table(name = "id_card")
public class IdCard implements Serializable {

	private static final long serialVersionUID = -1728214073773690607L;
	// 用户ID
	@Id
	@Column(name = "uid")
	public Long uid;

	// 年龄
	@Column(name = "age")
	public Integer age;

	// 性别:1-男,2-女
	@Column(name = "sex")
	public Integer sex;

	// 所在城市
	@Column(name = "city")
	public String city;

	// 邮政编码
	@Column(name = "postcode")
	public Integer postcode;

	// 真实姓名
	@Column(name = "true_name")
	public String trueName;

	// 身份证号
	@Column(name = "card_code")
	public String cardCode;

	// 身份证正面照保存路径
	@Column(name = "card_positive_img")
	public String cardPositiveImg;

	// 身份证背面照保存路径
	@Column(name = "card_back_img")
	public String cardBackImg;

	// 身份证手持照保存地址
	@Column(name = "card_hand_img")
	public String cardHandImg;

	@Column(name = "create_time")
	public Date createTime;

	@Column(name = "update_time")
	public Date updateTime;


	public IdCard(){}
}