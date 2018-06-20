package cn.itcast.service.product.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import cn.itcast.bean.product.ProductStyle;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.product.ProductStyleService;
@Service
public class ProductStyleServiceBean extends DaoSupport<ProductStyle> implements ProductStyleService {

	@Override
	public void setVisibleStatu(String[] stylesids, boolean statu) {
		if(stylesids!=null && stylesids.length>0){
			StringBuffer jpql = new StringBuffer();
			for(int i=0;i<stylesids.length;i++){
				jpql.append('?').append((i+2)).append(',');
			}
			jpql.deleteCharAt(jpql.length()-1);
			Query query = em.createQuery("update ProductStyle o set o.visible=?1 where o.id in("+ jpql.toString()+ ")");
			query.setParameter(1, statu);
			for(int i=0;i<stylesids.length;i++){
				query.setParameter(i+2, Integer.valueOf(stylesids[i]));
			}
			query.executeUpdate();
		}
	}

}
