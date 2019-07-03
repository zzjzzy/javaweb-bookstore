package com.itcast.bookstore.category.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bookstore.category.domain.Category;
import com.itcast.bookstore.category.service.CategoryException;
import com.itcast.bookstore.category.service.CategoryService;

@WebServlet("/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private CategoryService categoryService = new CategoryService();

	public String modify(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		try {
			Map<String, String> errors = verify(request);
			if (errors.size() > 0) {
				request.setAttribute("errors", errors);
				request.setAttribute("category", category);
				return "f:/adminjsps/admin/category/mod.jsp";
			}
			categoryService.modify(category);
			return findAll(request, response);
		} catch (CategoryException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("category", category);
			return "f:/adminjsps/admin/category/mod.jsp";
		}
	}
	
	public String preMod(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Category category = categoryService.findByCid(request.getParameter("cid"));
		request.setAttribute("category", category);
		return "f:/adminjsps/admin/category/mod.jsp";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());
		try {
			Map<String, String> errors = verify(request);
			if (errors.size() > 0) {
				request.setAttribute("errors", errors);
				return "f:/adminjsps/admin/category/add.jsp";
			}
			categoryService.add(category);
			return findAll(request, response);
		} catch (CategoryException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("cname",request.getParameter("cname"));
			return "f:/adminjsps/admin/category/add.jsp";
		}

	}
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		List<Category> categoryList = categoryService.findAll();
		request.setAttribute("categoryList", categoryList);
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		try {
			categoryService.delelte(cid);
			return findAll(request, response);
		} catch (CategoryException e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/adminjsps/msg.jsp";
		}

	}
	
	private Map<String, String> verify(HttpServletRequest request) {
		Map<String, String> errors = new HashMap<String, String>();
		String cname = request.getParameter("cname");
		if (cname == null || cname.trim().isEmpty()) {
			errors.put("cname", "分类名不能为空");
		}
		return errors;
	}
}
