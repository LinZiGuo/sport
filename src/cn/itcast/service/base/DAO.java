package cn.itcast.service.base;

import java.io.Serializable;
/**
 * 实体操作通用接口
 * @author 郭子灵
 *
 * @param <T>	实体类型
 */
public interface DAO<T> {
	
	/**
	 * 保存实体
	 * @param entity	实体对象
	 */
	public void save(T entity);
	
	/**
	 * 更新实体
	 * @param entity	实体对象
	 */
	public void update(T entity);
	
	/**
	 * 删除实体
	 * @param entityid	实体标识
	 */
	public void delete(Serializable entityid);//JPA 规定实体对象必须实现序列化接口
	
	/**
	 * 获取实体
	 * @param entityid	实体标记
	 * @return 			实体对象
	 */
	public T find(Serializable entityid);
	
	/**
	 * 获取实体的总记录数
	 * @return			总记录数
	 */
	public long getCount();
}
