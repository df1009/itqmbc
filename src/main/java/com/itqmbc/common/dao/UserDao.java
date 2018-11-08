package com.itqmbc.common.dao;

import java.util.List;

import com.itqmbc.common.entityBean.UserBean;


public interface UserDao {
	
	List<UserBean> findAll();
}
