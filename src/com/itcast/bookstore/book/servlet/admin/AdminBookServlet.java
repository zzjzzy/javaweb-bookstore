package com.itcast.bookstore.book.servlet.admin;

import cn.itcast.servlet.BaseServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bookstore.book.service.BookService;
import com.itcast.bookstore.category.service.CategoryService;

/**
 * Servlet implementation class AdminBookServlet
 */
@WebServlet("/admin/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	
	public String findBybid(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		request.setAttribute("book", bookService.load(bid));
		request.setAttribute("categoryList", categoryService.findAll());
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setAttribute("bookList", bookService.findAll());
		return "f:/adminjsps/admin/book/list.jsp";
	}	
	
	public String del(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		bookService.delete(bid);
		return findAll(request, response);
	}

}
