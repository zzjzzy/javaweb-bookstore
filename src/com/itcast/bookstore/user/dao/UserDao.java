package com.itcast.bookstore.user.dao;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {

	QueryRunner qr = new TxQueryRunner();
}
