package cn.itcast.service.product;

import cn.itcast.bean.product.ProductStyle;
import cn.itcast.service.base.DAO;

public interface ProductStyleService extends DAO<ProductStyle> {

	/**
	 * 样式上架
	 * @param stylesids	样式编号
	 * @param statu		可见状态
	 */
	void setVisibleStatu(String[] stylesids, boolean statu);

}
