package com.itcast.bookstore.book.servlet.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bookstore.book.service.BookService;

/**
 * Servlet implementation class AdminAddBookServlet
 */
@WebServlet("/admin/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("uft-8");
		response.setContentType("text/html;charset=utf-8");
	}

}
