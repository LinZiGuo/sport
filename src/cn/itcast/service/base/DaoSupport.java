package cn.itcast.service.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.utils.GenericsUtils;

@Transactional
public abstract class DaoSupport<T> implements DAO<T> {

	@PersistenceContext
	protected EntityManager em;
	//实体类
	@SuppressWarnings("unchecked")
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	
	public String getSubClass() {
		return getClass().getName();
	}
	
//	public Class<T> getEntityClass() {
//		Type parentType = getClass().getGenericSuperclass();
//		if (parentType instanceof ParameterizedType) {
//			ParameterizedType pType = (ParameterizedType) parentType;
//			return (Class<T>)pType.getActualTypeArguments()[0];
//		}
//		return null;
//	}
	
	public String getEntityName() {
		String entityName = this.entityClass.getSimpleName();
		Entity entity = this.entityClass.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityName = entity.name();
		}
		return entityName;
	}
	
	@Override
	public void save(T entity) {
		em.persist(entity);
	}

	@Override
	public void update(T entity) {
		em.merge(entity);
	}

	@Override
	public void delete(Serializable entityid) {
		em.remove(em.getReference(entityClass, entityid));
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public T find(Serializable entityid) {
		return em.find(entityClass, entityid);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public long getCount() {
		return (long) em.createQuery("select count(o) from " + getEntityName() + " o").getSingleResult();
	}

}
