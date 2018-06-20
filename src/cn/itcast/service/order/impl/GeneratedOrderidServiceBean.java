package cn.itcast.service.order.impl;

import org.springframework.stereotype.Service;

import cn.itcast.bean.order.GeneratedOrderid;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.order.GeneratedOrderidService;
@Service
public class GeneratedOrderidServiceBean extends DaoSupport<GeneratedOrderid> implements GeneratedOrderidService {

	public int buildOrderid(){
		em.createQuery("update GeneratedOrderid o set o.orderid=orderid+1 where o.id=?1")
			.setParameter(1, "order").executeUpdate();
		em.flush();
		GeneratedOrderid go = this.find("order");
		return go.getOrderid();
	}

//	@Override
//	@PostConstruct
//	public void init() {
//		if(this.getCount()==0){
//			GeneratedOrderid go = new GeneratedOrderid();
//			go.setId("order");
//			this.save(go);
//		}
//	}
}
