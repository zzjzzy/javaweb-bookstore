package com.itcast.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itcast.bookstore.book.domain.Book;
import com.itcast.bookstore.category.domain.Category;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {

	private QueryRunner qr = new TxQueryRunner();
	
	public List<Book> findAll(){
		try {
			String sql = "select * from book";
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Book> findByCategory(String cid){
		try {
			String sql = "select * from book where cid = ?";
			return qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Book findById(String bid) {
		try {
			String sql = "select * from book where bid = ?";
			Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
			Category category = CommonUtils.toBean(map, Category.class);
			Book book = CommonUtils.toBean(map, Book.class);
			book.setCategory(category);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int findBookCount(String cid) {
		String sql = "select count(*) from book where cid = ?";
		Number number;
		try {
			number = (Number) qr.query(sql, new ScalarHandler(), cid);
			return number.intValue();
		} catch (SQLException e) {
			throw new RuntimeException();
		}

	}
	
	public void delete(String bid) {
		String sql = "delete from book where bid = ?";
		try {
			qr.update(sql, bid);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public void edit(Book book) {
		String sql = "update book set bname=?, author=?, price=?, cid=?, image=? where bid = ?";
		try {
			Object[] params = {book.getBname(), book.getAuthor(), 
					book.getPrice(), book.getCategory().getCid(), 
					book.getImage(), book.getBid()};
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
