package com.itcast.bookstore.cart.domain;

import java.math.BigDecimal;

import com.itcast.bookstore.book.domain.Book;

public class CartItem {

	private Book book;
	private int count;
	public double getSubTotal() {
		BigDecimal d1 = new BigDecimal(count+"");
		BigDecimal d2 = new BigDecimal(book.getPrice()+"");
		return d1.multiply(d2).doubleValue();
	}
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
