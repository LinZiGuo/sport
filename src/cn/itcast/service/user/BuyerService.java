package cn.itcast.service.user;

import java.io.Serializable;

import cn.itcast.bean.user.Buyer;
import cn.itcast.service.base.DAO;

/**
 * 用户业务处理类
 * @author 郭子灵
 *
 */
public interface BuyerService extends DAO<Buyer> {
	/**
	 * 启用用户
	 * @param entityids	用户账号
	 */
	public void enable(Serializable... entityids);

	/**
	 * 校验用户名和密码是否正确
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean validate(String username, String password);
	
	/**
	 * 判断用户名是否存在
	 * @param username
	 * @return
	 */
	public boolean exsit(String username);
}
