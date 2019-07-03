package com.itcast.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import com.itcast.bookstore.order.dao.OrderDao;
import com.itcast.bookstore.order.domain.Order;

import cn.itcast.jdbc.JdbcUtils;

public class OrderService {

	private OrderDao orderDao = new OrderDao();
	
	public void add(Order order) {
		try {
			JdbcUtils.beginTransaction();
			
			orderDao.addOrder(order);
			orderDao.addOrderItem(order.getOrderItemList());
			
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	public List<Order> myOrders(String uid) {
		return orderDao.findByUid(uid);
	}

	public Order load(String oid) {
		return orderDao.load(oid);
	}
	
	public void confirm(String oid) throws OrderException {
		int state = orderDao.getStateByOid(oid);
		if (state != 3) {
			throw new OrderException("确认收货失败！你个骗子！");
		}
		orderDao.updateState(oid, 4);
	}

	public void zhiFu(String oid) {
		int state = orderDao.getStateByOid(oid);
		if(state == 1) {
			// 修改订单状态为2
			orderDao.updateState(oid, 2);
		}
	}
}
