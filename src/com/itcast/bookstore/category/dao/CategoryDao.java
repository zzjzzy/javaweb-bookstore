package com.itcast.bookstore.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itcast.bookstore.book.dao.BookDao;
import com.itcast.bookstore.category.domain.Category;

import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {

	QueryRunner qr = new TxQueryRunner();

	BookDao bookDao = new BookDao();
	
	public List<Category> findAll() {
		String sql = "select * from category";
		try {
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String cid) {
		String sql = "delete from category where cid = ?";
		try {
			qr.update(sql, cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int findBookCount(String cid) {
		return bookDao.findBookCount(cid);
	}

	public void add(Category category) {
		String sql = "insert into category values(?, ?)";
		try {
			qr.update(sql, category.getCid(), category.getCname());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public Category findByName(String cname) {
		String sql = "select * from category where cname = ?";
		try {
			return qr.query(sql, new BeanHandler<Category>(Category.class), cname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public Category findByCid(String cid) {
		String sql = "select * from category where cid = ?";
		try {
			return qr.query(sql, new BeanHandler<Category>(Category.class), cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public void modify(Category category) {
		String sql = "update category set cname = ? where cid = ?";
		try {
			qr.update(sql, category.getCname(), category.getCid());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
