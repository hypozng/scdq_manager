package com.scdq.manager.service;

import javax.servlet.http.HttpSession;

import com.scdq.manager.dao.UserDao;
import com.scdq.manager.model.ResponseData;
import com.scdq.manager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scdq.manager.common.Constants;
import com.scdq.manager.dao.ConstantDao;

@Service
public class SystemService {
	@Autowired
	private ConstantDao constantDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HttpSession session;
	
	public String getProperty(String key) {
		return constantDao.get(key);
	}
	
	public boolean removeProperty(String key) {
		return constantDao.remove(key) > 0;
	}
	
	public boolean setProperty(String key, String value) {
		int result = 0;
		if (constantDao.contains(key)) {
			result = constantDao.update(key, value);
		} else {
			result = constantDao.insert(key, value);
		}
		return result > 0;
	}
	
	/**
	 * 登录系统
	 * @param account 账号(用户名/手机号/邮箱)
	 * @param password 密码
	 * @return
	 */
	public ResponseData login(String account, String password) {
		User user = userDao.getByUsername(account);
		if (user == null) {
			user = userDao.getByPhone(account);
		}
		if (user == null) {
			user = userDao.getByEmail(account);
		}
		if (user == null) {
			return new ResponseData("该账号不存在");
		}
		boolean result = userDao.verifyPassword(user, password);
		if (!result) {
			return new ResponseData("密码错误");
		}
		session.setAttribute(Constants.LOGIN_USER, user);
		return new ResponseData<User>(user);
	}

	/**
	 * 获取当前登录的用户
	 * @return
	 */
	public User getLoginUser() {
		Object user = session.getAttribute(Constants.LOGIN_USER);
		if (user instanceof User) {
			return (User) user;
		}
		return null;
	}
	
	/**
	 * 已登录返回true，未登录返回false
	 * @return
	 */
	public boolean isLogin() {
		return getLoginUser() != null;
	}
	
	/**
	 * 取消登录
	 */
	public ResponseData logout() {
		session.removeAttribute(Constants.LOGIN_USER);
		return ResponseData.SUCCESSFUL;
	}
	
	/**
	 * 更改密码
	 * @param original  原来的密码
	 * @param now 现在的密码
	 * @return 返回true标识修改成功  返回false标识修改失败
	 */
	public ResponseData modifyPassword(String original, String now) {
		User user = getLoginUser();
		if (user == null) {
			return new ResponseData("尚未登录，无法修改密码");
		}
		if (!userDao.verifyPassword(user, original)) {
			return new ResponseData("原密码错误");
		}
		if (!userDao.modifyPassword(user, now)) {
			return new ResponseData("数据库错误");
		}
		logout();
		return ResponseData.SUCCESSFUL;
	}
}
