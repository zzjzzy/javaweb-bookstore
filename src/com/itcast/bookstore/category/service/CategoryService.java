package com.itcast.bookstore.category.service;

import java.util.List;

import com.itcast.bookstore.category.dao.CategoryDao;
import com.itcast.bookstore.category.domain.Category;

public class CategoryService {

	CategoryDao categoryDao = new CategoryDao();

	public List<Category> findAll() {
		return categoryDao.findAll();
	}
}
