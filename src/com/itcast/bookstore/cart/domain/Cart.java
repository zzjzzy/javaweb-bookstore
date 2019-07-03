package com.itcast.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	
	public double getTotal() {
		BigDecimal total = new BigDecimal("0");
		for (CartItem item : map.values()) {
			BigDecimal subTotal = new BigDecimal(item.getSubTotal()+"");
			total = total.add(subTotal);
		}
		return total.doubleValue();
	}
	
	public void add(CartItem cartItem) {
		if (map.containsKey(cartItem.getBook().getBid())) {
			CartItem _cartItem = map.get(cartItem.getBook().getBid());
			_cartItem.setCount(cartItem.getCount() + _cartItem.getCount());
			map.put(cartItem.getBook().getBid(), _cartItem);
		} else {
			map.put(cartItem.getBook().getBid(), cartItem);
		}
	}
	
	public void clear() {
		map.clear();
	}
	
	public void delete(String bid) {
		map.remove(bid);
	}
	
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
	
}
