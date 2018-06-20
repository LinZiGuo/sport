package cn.itcast.service.order;

import cn.itcast.bean.order.GeneratedOrderid;
import cn.itcast.service.base.DAO;

public interface GeneratedOrderidService extends DAO<GeneratedOrderid> {
	/**
	 * 生成订单流水号
	 * @return
	 */
	public int buildOrderid();
	
	/**
	 * 初始化
	 */
//	public void init();
}
