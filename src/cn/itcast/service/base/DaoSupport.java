package cn.itcast.service.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bean.QueryResult;
import cn.itcast.utils.GenericsUtils;

@SuppressWarnings("unchecked")
@Transactional
public abstract class DaoSupport<T> implements DAO<T> {

	@PersistenceContext
	protected EntityManager em;
	//实体类
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	
	/**
	 * 得到所有数据
	 * @return
	 */
	public QueryResult<T> getScrollData(){
		return getScrollData(-1, -1, null, null, null);
	}
	
	/**
	 * 得到分页数据
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxResult){
		return getScrollData(firstResult, maxResult, null, null, null);
	}
	
	/**
	 * 得到分页排序数据
	 * @param firstResult
	 * @param maxResult
	 * @param orderby
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxResult, LinkedHashMap<String, String> orderby){
		return getScrollData(firstResult, maxResult, null, null, orderby);
	}
	
	/**
	 * 得到分页条件查询数据
	 * @param firstResult
	 * @param maxResult
	 * @param where
	 * @param params
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxResult, String where, Object[] params){
		return getScrollData(firstResult, maxResult, where, params, null);
	}
	
	/**
	 * 得到分页条件查询排序数据
	 * @param firstResult
	 * @param maxResult
	 * @param where
	 * @param params
	 * @param orderby
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxResult, String where, Object[] params, LinkedHashMap<String, String> orderby) {
		String entityName = getEntityName(entityClass);
		String whereql = where != null && !"".equals(where.trim()) ? "where " + where+" " : "";
		String sql = "select o from " + entityName + " o " + whereql + buildOrderby(orderby);
		Query query = em.createQuery(sql);
		if (firstResult != -1 && maxResult != -1) {
			query.setFirstResult(firstResult).setMaxResults(maxResult);			
		}
		setQueryParameter(query, params);
		QueryResult<T> qr =new QueryResult<>();
		qr.setResultlist(query.getResultList());
		query = em.createQuery("select count(o) from " + entityName + " o " + whereql);
		setQueryParameter(query, params);
		qr.setRecordtotal((Long)query.getSingleResult());
		return qr;
	}
	
	/**
	 * 设置查询参数
	 * @param query		查询对象
	 * @param params	参数值
	 */
	public static void setQueryParameter(Query query, Object[] params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i+1, params[i]);
			}
		}
	}
	
	/**
	 * 构建排序语句
	 * @param orderby	排序属性与asc/desc,Key为属性，value为asc/desc
	 * @return			排序语句
	 */
	public static String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuilder sb = new StringBuilder();
		if(orderby != null && !orderby.isEmpty()) {
			sb.append(" order by ");
			for (Map.Entry<String, String> entry : orderby.entrySet()) {
				sb.append("o.").append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
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
	
	public static <E> String getEntityName(Class<E> entityClass) {
		String entityName = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
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
	public void delete(Serializable... entityids) {
		for (Serializable id : entityids) {
			em.remove(em.getReference(entityClass, id));			
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public T find(Serializable entityid) {
		return em.find(entityClass, entityid);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public long getCount() {
		return (long) em.createQuery("select count(o) from " + getEntityName(this.entityClass) + " o").getSingleResult();
	}

}
