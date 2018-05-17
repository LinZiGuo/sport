package cn.itcast.service.product;

import java.util.List;

import cn.itcast.bean.product.ProductType;
import cn.itcast.service.base.DAO;

public interface ProductTypeService extends DAO<ProductType> {

	/**
	 * 获取子类别列表
	 * @param parentids	父类别id数组
	 * @return			子类别id数组
	 */
	public List<Integer> getSubTypeId(Integer[] parentids);
}
