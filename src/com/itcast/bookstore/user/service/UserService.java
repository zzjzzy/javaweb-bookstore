package com.itcast.bookstore.user.service;

import com.itcast.bookstore.user.dao.UserDao;
import com.itcast.bookstore.user.domain.User;

public class UserService {

	UserDao userDao = new UserDao();
	
	public void regist(User form) throws UserException {
		User user = userDao.findByUsername(form.getUsername());
		if (user != null) {
			throw new UserException("用户名已被注册");
		}
		
		user = userDao.findByEmail(form.getEmail());
		if (user != null) {
			throw new UserException("邮箱已被注册");
		}
		
		userDao.add(form);
	}
	
	public void active(String code) throws UserException {
		User user = userDao.findByCode(code);
		if (user == null) {
			throw new UserException("激活码错误！");
		}
		if (user.isState()) {
			throw new UserException("已激活，请勿重新激活");
		}
		
		userDao.updateState(user.getUid(), true);
	}
	
	public User login(User form) throws UserException {
		User user = userDao.findByUsername(form.getUsername());
		if (user == null) {
			throw new UserException("用户名不存在");
		}
		if (!user.getPassword().equals(form.getPassword())) {
			throw new UserException("密码错误");
		}
		if (user.isState() == false) {
			throw new UserException("您还未激活，无法登录");
		}
		return user;
	}
}





