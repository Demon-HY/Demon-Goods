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

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getPostcode() {
		return postcode;
	}

	public void setPostcode(Integer postcode) {
		this.postcode = postcode;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardPositiveImg() {
		return cardPositiveImg;
	}

	public void setCardPositiveImg(String cardPositiveImg) {
		this.cardPositiveImg = cardPositiveImg;
	}

	public String getCardBackImg() {
		return cardBackImg;
	}

	public void setCardBackImg(String cardBackImg) {
		this.cardBackImg = cardBackImg;
	}

	public String getCardHandImg() {
		return cardHandImg;
	}

	public void setCardHandImg(String cardHandImg) {
		this.cardHandImg = cardHandImg;
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

	@Override
	public String toString() {
		return "IdCard{" +
				"uid=" + uid +
				", age=" + age +
				", sex=" + sex +
				", city='" + city + '\'' +
				", postcode=" + postcode +
				", trueName='" + trueName + '\'' +
				", cardCode='" + cardCode + '\'' +
				", cardPositiveImg='" + cardPositiveImg + '\'' +
				", cardBackImg='" + cardBackImg + '\'' +
				", cardHandImg='" + cardHandImg + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}