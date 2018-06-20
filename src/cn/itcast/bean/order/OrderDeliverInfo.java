package cn.itcast.bean.order;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import cn.itcast.bean.user.Gender;

/**
 * 配送信息
 * @author 郭子灵
 *
 */
@Entity
public class OrderDeliverInfo {
	/* 配送id */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer deliverid;
	/* 收货人姓名 */
	@Column(length=8,nullable=false)
	private String recipients;
	/* 配送地址 */
	@Column(length=40,nullable=false)
	private String address;
	/* 电子邮箱 */
	@Column(length=40)
	private String email;
	/* 邮编 */
	@Column(length=6)
	private String postalcode;
	/* 座机 */
	@Column(length=18)
	private String tel;
	/* 手机 */
	@Column(length=11)
	private String mobile;
	/* 性别 */
	@Enumerated(EnumType.STRING)
	@Column(length=5,nullable=false)
	private Gender gender = Gender.MAN;
	/* 送货方式 */
	@Enumerated(EnumType.STRING)
	@Column(length=23,nullable=false)
	private DeliverWay deliverWay;
	/* 时间要求 */
	@Column(length=30)
	private String requirement;
	/* 所属的订单 */
	@OneToOne(mappedBy="orderDeliverInfo", cascade=CascadeType.REFRESH)
	private Order order;
	public Integer getDeliverid() {
		return deliverid;
	}
	public void setDeliverid(Integer deliverid) {
		this.deliverid = deliverid;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public DeliverWay getDeliverWay() {
		return deliverWay;
	}
	public void setDeliverWay(DeliverWay deliverWay) {
		this.deliverWay = deliverWay;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deliverid == null) ? 0 : deliverid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDeliverInfo other = (OrderDeliverInfo) obj;
		if (deliverid == null) {
			if (other.deliverid != null)
				return false;
		} else if (!deliverid.equals(other.deliverid))
			return false;
		return true;
	}
	
}
