package cn.itcast.service.product;

import cn.itcast.bean.product.ProductInfo;
import cn.itcast.service.base.DAO;

public interface ProductInfoService extends DAO<ProductInfo> {

	/**
	 * 设置商品上架/下架
	 * @param productids	商品id
	 * @param statu
	 */
	void setVisibleStatu(String[] productids, boolean statu);

	/**
	 * 设置商品推荐/取消推荐
	 * @param productids
	 * @param statu
	 */
	void setCommendStatu(String[] productids, boolean statu);

}
