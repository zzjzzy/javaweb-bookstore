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
}
