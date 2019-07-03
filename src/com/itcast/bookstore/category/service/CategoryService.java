package com.itcast.bookstore.category.service;

import java.util.List;

import com.itcast.bookstore.category.dao.CategoryDao;
import com.itcast.bookstore.category.domain.Category;

public class CategoryService {

	CategoryDao categoryDao = new CategoryDao();

	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	public void delelte(String cid) throws CategoryException {
		int count = categoryDao.findBookCount(cid);
		if (count > 0) {
			throw new CategoryException("该分类下有图书，不能删除");
		}
		categoryDao.delete(cid);
		
	}

	public void add(Category category) throws CategoryException {
		Category c = categoryDao.findByName(category.getCname());
		if (c != null) {
			throw new CategoryException("分类已存在，不能重复添加");
		}
		categoryDao.add(category);
	}
	
	public Category findByCid(String cid) {
		return categoryDao.findByCid(cid);
	}
	
	public void modify(Category category) throws CategoryException {
		Category c = categoryDao.findByName(category.getCname());
		if (c != null) {
			throw new CategoryException("分类名已存在，不能修改为此分类名");
		}
		categoryDao.modify(category);
	}
}
