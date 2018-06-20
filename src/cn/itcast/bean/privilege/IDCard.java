package cn.itcast.bean.privilege;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
public class IDCard {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;//实体标识,自增长
	/* 18 ,不能为null */
	@Column(length=18,nullable=false)
	private String cardno;
	/* 50 不能为null */
	@Column(length=40,nullable=false)
	private String address;
	/* 出生日期 */
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date birthday;//采用只含有日期部分的类型表示
	/* 所属的员工 */
	@OneToOne(mappedBy="idCard", cascade=CascadeType.REFRESH)
	private Employee employee;
	
	public IDCard() {
	}
	
	public IDCard(String cardno, String address, Date birthday) {
		this.cardno = cardno;
		this.address = address;
		this.birthday = birthday;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final IDCard other = (IDCard) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
