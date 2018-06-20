package cn.itcast.service.order.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import cn.itcast.bean.order.Order;
import cn.itcast.bean.order.OrderItem;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.order.OrderItemService;
@Service
public class OrderItemServiceBean extends DaoSupport<OrderItem> implements OrderItemService {

	@Override
	public void delete(Serializable... entityids) {
		for(Serializable itemid : entityids){
			OrderItem item = this.find(itemid);			
			Order order = item.getOrder();
			order.getItems().remove(item);
			float result = 0f;
			for(OrderItem oItem : order.getItems()){
				result += oItem.getProductPrice() * oItem.getAmount();
			}
			order.setProductTotalPrice(result);
			order.setTotalPrice(order.getProductTotalPrice()+ order.getDeliverFee());
			order.setPayablefee(order.getTotalPrice());
			em.remove(item);
		}
	}

	/**
	 * 更新商品购买数量
	 * @param itemid 订单项
	 * @param amount 购买数量
	 */
	@Override
	public void updateAmount(Integer orderitemid, Integer amount) {
		OrderItem item = this.find(orderitemid);
		item.setAmount(amount);
		Order order = item.getOrder();
		float result = 0f;
		for(OrderItem oItem : order.getItems()){
			result += oItem.getProductPrice() * oItem.getAmount();
		}
		order.setProductTotalPrice(result);
		order.setTotalPrice(order.getProductTotalPrice()+ order.getDeliverFee());
		order.setPayablefee(order.getTotalPrice());
	}

}
