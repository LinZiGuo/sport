package cn.itcast.service.product;

import java.util.List;

import cn.itcast.bean.product.Brand;
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

	/**
	 * 获取类别下产品所使用到的品牌
	 * @param typeids 产品类别id
	 * @return
	 */
	public List<Brand> getBrandsByProductTypeid(Integer[] typeids);

	/**
	 * 获取销量最多并且被推荐的产品
	 * @param typeid	类别id
	 * @param maxResult	获取的产品数量
	 * @return			产品列表
	 */
	public List<ProductInfo> getTopSell(Integer typeid, int maxResult);

	/**
	 * 获取指定ID的产品
	 * @param productids	产品id数组
	 * @param maxResult		最大获取多少条记录
	 * @return				产品列表
	 */
	public List<ProductInfo> getViewHistory(Integer[] productids, int maxResult);
}
