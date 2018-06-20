package cn.itcast.bean.order;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.itcast.bean.user.Buyer;

/**
 * 订单
 * @author 郭子灵
 *
 */
@Entity
@Table(name="t_order")
public class Order {
	/* 订单号 */
	@Id
	@Column(length=14)
	private String orderid;
	/* 所属用户 */
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	@JoinColumn(name="username")
	private Buyer buyer;
	/* 订单创建时间 */
	@Temporal(TemporalType.TIMESTAMP) @Column(nullable=false)
	private Date createDate = new Date();
	/* 订单状态 */
	@Enumerated(EnumType.STRING) @Column(length=16, nullable=false)
	private OrderState state;
	/* 商品总金额 */
	@Column(nullable=false)
	private Float productTotalPrice = 0f;
	/* 配送费 */
	@Column(nullable=false)
	private Float deliverFee = 0f;
	/* 订单总金额 */
	@Column(nullable=false)
	private Float totalPrice= 0f;
	/* 应付款(实际需要支付的费用) */
	@Column(nullable=false)
	private Float payablefee = 0f;
	/* 顾客附言 */
	@Column(length=100)
	private String note;
	/* 支付方式 */
	@Enumerated(EnumType.STRING)
	@Column(length=20,nullable=false)
	private PaymentWay paymentWay;
	/* 支付状态 */
	@Column(nullable=false)
    private Boolean paymentstate = false;
    /* 订单配送信息 */
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="deliver_id")
	private OrderDeliverInfo orderDeliverInfo;
	/* 订单购买者联系信息 */
	@OneToOne(cascade=CascadeType.ALL, optional=false)
	@JoinColumn(name="contact_id")
	private OrderContactInfo orderContactInfo;
	/* 订单项 */
	@OneToMany(mappedBy="order",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<OrderItem> items = new HashSet<OrderItem>();
	/* 对订单进行加锁的用户,如果值为null,代表订单未被加锁,否则,订单被加锁 */
	@Column(length=20)
	private String lockuser;
	/* 客服留言 */
	@OneToMany(mappedBy="order",cascade=CascadeType.REMOVE,fetch=FetchType.EAGER)
	private Set<Message> msgs = new HashSet<Message>();
	
	public Order() {
	}
	public Order(String orderid) {
		this.orderid = orderid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public OrderState getState() {
		return state;
	}
	public void setState(OrderState state) {
		this.state = state;
	}
	public Float getProductTotalPrice() {
		return productTotalPrice;
	}
	public void setProductTotalPrice(Float productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}
	public Float getDeliverFee() {
		return deliverFee;
	}
	public void setDeliverFee(Float deliverFee) {
		this.deliverFee = deliverFee;
	}
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Float getPayablefee() {
		return payablefee;
	}
	public void setPayablefee(Float payablefee) {
		this.payablefee = payablefee;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public PaymentWay getPaymentWay() {
		return paymentWay;
	}
	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}
	public Boolean getPaymentstate() {
		return paymentstate;
	}
	public void setPaymentstate(Boolean paymentstate) {
		this.paymentstate = paymentstate;
	}
	public OrderDeliverInfo getOrderDeliverInfo() {
		return orderDeliverInfo;
	}
	public void setOrderDeliverInfo(OrderDeliverInfo orderDeliverInfo) {
		this.orderDeliverInfo = orderDeliverInfo;
	}
	public OrderContactInfo getOrderContactInfo() {
		return orderContactInfo;
	}
	public void setOrderContactInfo(OrderContactInfo orderContactInfo) {
		this.orderContactInfo = orderContactInfo;
	}
	public Set<OrderItem> getItems() {
		return items;
	}
	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}
	public String getLockuser() {
		return lockuser;
	}
	public void setLockuser(String lockuser) {
		this.lockuser = lockuser;
	}
	public Set<Message> getMsgs() {
		return msgs;
	}
	public void setMsgs(Set<Message> msgs) {
		this.msgs = msgs;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderid == null) ? 0 : orderid.hashCode());
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
		Order other = (Order) obj;
		if (orderid == null) {
			if (other.orderid != null)
				return false;
		} else if (!orderid.equals(other.orderid))
			return false;
		return true;
	}
	/**
	 * 添加订单项
	 * @param item
	 */
	public void addOrderItem(OrderItem item){
		this.items.add(item);
		item.setOrder(this);
	}
}
