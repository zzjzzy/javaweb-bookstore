package com.itcast.bookstore.book.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bookstore.book.service.BookService;

import cn.itcast.servlet.BaseServlet;

@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet{

	private BookService bookService = new BookService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("bookList", bookService.findAll());
		return "/jsps/book/list.jsp";
	}
	
	public String findByCategory(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String cid = request.getParameter("cid");
		request.setAttribute("bookList", bookService.findByCategory(cid));
		return "f:/jsps/book/list.jsp";
	}
	
	public String load(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String bid = request.getParameter("bid");
		request.setAttribute("book", bookService.load(bid));
		return "f:/jsps/book/desc.jsp";
	}
	
}
