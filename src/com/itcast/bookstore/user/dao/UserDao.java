package com.itcast.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itcast.bookstore.user.domain.User;

import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {

	QueryRunner qr = new TxQueryRunner();
	
	public User findByUsername(String username) {
		try {
			String sql = "select * from tb_user where username = ?";
			return qr.query(sql, new BeanHandler<User>(User.class), username);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User findByEmail(String email) {
		try {
			String sql = "select * from tb_user where email = ?";
			return qr.query(sql, new BeanHandler<User>(User.class), email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User findByCode(String code) {
		try {
			String sql = "select * from tb_user where code = ?";
			return qr.query(sql, new BeanHandler<User>(User.class), code);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updateState(String uid, boolean state) {
		try {
			String sql = "update tb_user set state = ? where uid = ?";
			qr.update(sql, state, uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void add(User user) {
		try {
			String sql = "insert into tb_user values(?,?,?,?,?,?)";
			Object[] params = {user.getUid(), user.getUsername(), user.getPassword(),
					user.getEmail(), user.getCode(), user.isState()};
			qr.update(sql, params );
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
