package cn.itcast.bean.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.internal.Nullable;

/**
 * 用户实体
 * @author 郭子灵
 *
 */
@Entity
public class Buyer implements Serializable{
	/* 用户名 */
	@Id
	@Column(length=20)
	private String username;//只允许字母/数字/下划线
	/* 密码 */
	@Column(length=32, nullable=false)
	private String password;//MD5加密
	/* 真实姓名 */
	@Column(length=20)
	private String realname;
	/* 电子邮箱 */
	@Column(length=45,nullable=false)
	private String email;
	/* 性别 */
	@Enumerated(EnumType.STRING)
	@Column(length=5)
	private Gender gender=Gender.MAN;
	/* 联系方式 */
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="contact_id")
	private ContactInfo contactInfo;
	/* 是否启用 */
	@Column(nullable=false)
	private Boolean visible=true;
	/* 注册时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date regTime = new Date();
	public Buyer() {
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public ContactInfo getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Buyer other = (Buyer) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
