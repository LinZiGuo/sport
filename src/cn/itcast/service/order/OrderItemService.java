package cn.itcast.service.order;

import cn.itcast.bean.order.OrderItem;
import cn.itcast.service.base.DAO;

public interface OrderItemService extends DAO<OrderItem> {

	/**
	 * 更新商品购买数量
	 * @param itemid 订单项
	 * @param amount 购买数量
	 */
	public void updateAmount(Integer orderitemid, Integer amount);

}
