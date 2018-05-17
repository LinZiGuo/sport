package cn.itcast.service.product.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bean.product.ProductType;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.product.ProductTypeService;
@Service
public class ProductTypeServiceBean extends DaoSupport<ProductType> implements ProductTypeService {
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<Integer> getSubTypeId(Integer[] parentids) {
		if (parentids!=null&&parentids.length>0) {
			StringBuffer jqpl = new StringBuffer();
			for (int i = 0; i < parentids.length; i++) {
				jqpl.append("?").append(i+1).append(",");
			}
			jqpl.deleteCharAt(jqpl.length()-1);
			Query query = em.createQuery("select o.typeid from ProductType o where o.parent.typeid in ("+ jqpl.toString() +")");
			for (int i = 0; i < parentids.length; i++) {
				query.setParameter(i+1, parentids[i]);
			}
			return query.getResultList();
		}
		return null;
	}
}
