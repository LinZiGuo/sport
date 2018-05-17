package cn.itcast.service.product.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import cn.itcast.bean.product.ProductInfo;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.product.ProductInfoService;
@Service
public class ProductInfoServiceBean extends DaoSupport<ProductInfo> implements ProductInfoService {

	@Override
	public void setVisibleStatu(String[] productids, boolean statu) {
		if(productids!=null && productids.length>0){
			StringBuffer jpql = new StringBuffer();
			for(int i=0;i<productids.length;i++){
				jpql.append('?').append((i+2)).append(',');
			}
			jpql.deleteCharAt(jpql.length()-1);
			Query query = em.createQuery("update ProductInfo o set o.commend=?1 where o.id in("+ jpql.toString()+ ")");
			query.setParameter(1, statu);
			for(int i=0;i<productids.length;i++){
				query.setParameter(i+2, Integer.valueOf(productids[i]));
			}
			query.executeUpdate();
		}
	}

	@Override
	public void setCommendStatu(String[] productids, boolean statu) {
		if(productids!=null && productids.length>0){
			StringBuffer jpql = new StringBuffer();
			for(int i=0;i<productids.length;i++){
				jpql.append('?').append((i+2)).append(',');
			}
			jpql.deleteCharAt(jpql.length()-1);
			Query query = em.createQuery("update ProductInfo o set o.commend=?1 where o.id in("+ jpql.toString()+ ")");
			query.setParameter(1, statu);
			for(int i=0;i<productids.length;i++){
				query.setParameter(i+2, Integer.valueOf(productids[i]));
			}
			query.executeUpdate();
		}
	}

}
