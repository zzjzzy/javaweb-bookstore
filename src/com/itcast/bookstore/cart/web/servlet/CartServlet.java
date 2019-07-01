package com.itcast.bookstore.cart.web.servlet;

import cn.itcast.servlet.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.bookstore.book.domain.Book;
import com.itcast.bookstore.book.service.BookService;
import com.itcast.bookstore.cart.domain.Cart;
import com.itcast.bookstore.cart.domain.CartItem;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String bid = request.getParameter("bid");
    	Book book = new BookService().load(bid);
    	int count = Integer.parseInt(request.getParameter("count"));
    	CartItem cartItem = new CartItem();
    	cartItem.setBook(book);
    	cartItem.setCount(count);
    	Cart cart = (Cart)request.getSession().getAttribute("cart");
    	cart.add(cartItem);
    	return "f:/jsps/cart/list.jsp";
    }
    
    public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Cart cart = (Cart)request.getSession().getAttribute("cart");
    	cart.clear();
    	return "f:/jsps/cart/list.jsp";
    }
    
    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Cart cart = (Cart)request.getSession().getAttribute("cart");
    	String bid = request.getParameter("bid");
    	cart.delete(bid);
    	return "f:/jsps/cart/list.jsp";
    }
}
