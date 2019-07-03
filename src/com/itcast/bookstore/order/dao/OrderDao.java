package com.itcast.bookstore.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itcast.bookstore.book.domain.Book;
import com.itcast.bookstore.order.domain.Order;
import com.itcast.bookstore.order.domain.OrderItem;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {

	private QueryRunner qr = new TxQueryRunner();
	
	public void addOrder(Order order) {
		String sql = "insert into orders values(?,?,?,?,?,?)";
		Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
		Object[] params = {order.getOid(), timestamp, order.getTotal(), 
				order.getState(), order.getOwner().getUid(), order.getAddress()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void addOrderItem(List<OrderItem> orderItemList) {
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object[][] params = new Object[orderItemList.size()][];
		for (int i = 0; i < orderItemList.size(); i++) {
			OrderItem orderItem = orderItemList.get(i);
			params[i] = new Object[]{orderItem.getIid(), orderItem.getCount(),
					orderItem.getSubtotal(), orderItem.getOrder().getOid(), 
					orderItem.getBook().getBid()};
		}
		
		try {
			qr.batch(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Order> findByUid(String uid) {
		try {
			String sql = "select * from orders where uid = ?";
			List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
			for (Order order : orderList) {
				loadOrderItems(order);
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	private void loadOrderItems(Order order) throws SQLException {
		String sql = "select * from orderitem i, book b where i.bid=b.bid and i.oid=?";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		order.setOrderItemList(orderItemList);
		
	}

	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for (Map<String, Object> map : mapList) {
			OrderItem item = toOrderItem(map);
			orderItemList.add(item);
		}
		return orderItemList;
	}

	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem item = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		item.setBook(book);
		return item;
	}

	public Order load(String oid) {
		String sql = "select * from orders where oid = ?";
		try {
			Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
			loadOrderItems(order);
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int getStateByOid(String oid) {
		try {
			String sql = "select state from orders where oid = ?";
			return (Integer) qr.query(sql, new ScalarHandler(), oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updateState(String oid, int state) {
		try {
			String sql = "update orders set state=? where oid=?";
			qr.update(sql, state, oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
