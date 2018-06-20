package cn.itcast.bean;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.bean.order.OrderContactInfo;
import cn.itcast.bean.order.OrderDeliverInfo;
import cn.itcast.bean.order.PaymentWay;

/**
 * 购物车
 * @author 郭子灵
 *
 */
public class BuyCart {
	/* 购物项 */
	private List<BuyItem> items = new ArrayList<BuyItem>();
	/* 收货人配送信息 */
	private OrderDeliverInfo deliverInfo;
	/* 订购者联系信息 */
	private OrderContactInfo contactInfo;
	/* 收货人与订购者是否相同 */
	private Boolean buyerIsrecipients;
	/* 支付方式 */
	private PaymentWay paymentWay;
	/* 配送费 */
	private float deliveFee = 10f;
	/* 附言 */
	private String note;
	
	public List<BuyItem> getItems() {
		return items;
	}
	public OrderDeliverInfo getDeliverInfo() {
		return deliverInfo;
	}
	public void setDeliverInfo(OrderDeliverInfo deliverInfo) {
		this.deliverInfo = deliverInfo;
	}
	public OrderContactInfo getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(OrderContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	public Boolean getBuyerIsrecipients() {
		return buyerIsrecipients;
	}
	public void setBuyerIsrecipients(Boolean buyerIsrecipients) {
		this.buyerIsrecipients = buyerIsrecipients;
	}
	public PaymentWay getPaymentWay() {
		return paymentWay;
	}
	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}
	public float getDeliveFee() {
		return deliveFee;
	}
	public void setDeliveFee(float deliveFee) {
		this.deliveFee = deliveFee;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 添加购物项
	 * @param item
	 */
	public void addBuyItem(BuyItem item){
		if(items.contains(item)){//如果购物项已经存在于购物车,累加其购买数量
			for(BuyItem bItem : items){
				if(bItem.equals(item)){
					bItem.setAmount(bItem.getAmount()+1);
					break;
				}
			}
		}else{
			items.add(item);
		}
	}
	/**
	 * 删除购物项
	 * @param item
	 */
	public void deleteBuyItem(BuyItem item){
		if(this.items.contains(item))
			this.items.remove(item);
	}
	/**
	 * 清空购物车
	 */
	public void deleteAll(){
		items.clear();
	}
	/**
	 * 计算商品的总金额
	 */
	public float getTotalSellPrice(){
		float result = 0f;
		for(BuyItem item : items){
			result += item.getProduct().getSellprice() * item.getAmount();
		}
		return result;
	}
	
	/**
	 * 计算商品的总节省金额
	 */
	public float getTotalSavePrice(){
		float result = 0f;
		for(BuyItem item : items){
			result += item.getProduct().getMarketprice() * item.getAmount();
		}
		return result - getTotalSellPrice();
	}
	/**
	 * 计算订单总金额
	 * @return
	 */
	public float getOrderTotalPrice(){
		return getTotalSellPrice() + getDeliveFee();
	}
}
