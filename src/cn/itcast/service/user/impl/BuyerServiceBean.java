package cn.itcast.service.user.impl;

import java.io.Serializable;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import cn.itcast.bean.user.Buyer;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.user.BuyerService;
import cn.itcast.utils.MD5;

@Service
public class BuyerServiceBean extends DaoSupport<Buyer> implements BuyerService {
	public void enable(Serializable... entityids) {
		setVisible(true, entityids);
	}
	@Override
	public void delete(Serializable... entityids) {
		setVisible(false, entityids);
	}

	private void setVisible(boolean visible, Serializable... entityids) {
		if (entityids!=null && entityids.length>0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < entityids.length; i++) {
				sb.append("?").append(i+2).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			Query query = em.createQuery("update Buyer o set o.visible=?1 where o.username in("+sb+")");
			query.setParameter(1, visible);
			for (int i = 0; i < entityids.length; i++) {
				query.setParameter(i+2, entityids[i]);
			}
			query.executeUpdate();
		}
	}
	

	public boolean validate(String username, String password) {
		long count = (Long)em.createQuery("select count(o) from Buyer o where o.username=?1 and password=?2")
				.setParameter(1, username).setParameter(2, MD5.MD5Encode(password)).getSingleResult();
		return count>0;
	}
	
	public boolean exsit(String username) {
		long count = (Long)em.createQuery("select count(o) from Buyer o where o.username=?1")
				.setParameter(1, username).getSingleResult();
		return count>0;
	}

	@Override
	public void save(Buyer entity) {
		entity.setPassword(MD5.MD5Encode(entity.getPassword()));
		super.save(entity);
	}
	
	
}
