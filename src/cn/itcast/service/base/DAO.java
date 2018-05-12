package cn.itcast.service.base;

import java.io.Serializable;
import java.util.LinkedHashMap;

import cn.itcast.bean.QueryResult;
/**
 * 实体操作通用接口
 * @author 郭子灵
 *
 * @param <T>	实体类型
 */
public interface DAO<T> {
	/**
	 * 得到所有数据
	 * @return
	 */
	public QueryResult<T> getScrollData();
	
	/**
	 * 得到分页数据
	 * @param firstResult 开始索引
	 * @param maxResult 每页获取的记录数
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxResult);
	
	/**
	 * 得到分页排序数据
	 * @param firstResult 开始索引
	 * @param maxResult 每页获取的记录数
	 * @param orderby Key为排序属性，Value为asc/desc
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxResult, LinkedHashMap<String, String> orderby);
	
	/**
	 * 得到分页条件查询数据
	 * @param firstResult 开始索引
	 * @param maxResult 每页获取的记录数
	 * @param where 条件语句，不带where关键字，条件语句只能使用位置参数，位置参数的索引值以1开始，如：o.username=?1 and o.password=?2
	 * @param params 条件语句出现的位置参数
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxResult, String where, Object[] params);
	
	/**
	 * 得到分页条件查询排序数据
	 * @param firstResult 开始索引,如果输入值为-1，即获取全部数据
	 * @param maxResult 每页获取的记录数,如果输入值为-1，即获取全部数据
	 * @param where 条件语句，不带where关键字，条件语句只能使用位置参数，位置参数的索引值以1开始，如：o.username=?1 and o.password=?2
	 * @param params 条件语句出现的位置参数
	 * @param orderby Key为排序属性，Value为asc/desc
	 * @return
	 */
	public QueryResult<T> getScrollData(int firstResult, int maxResult, String where, Object[] params, LinkedHashMap<String, String> orderby);
	
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
	public void delete(Serializable... entityids);//JPA 规定实体对象必须实现序列化接口
	
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
