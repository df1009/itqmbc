package com.itqmbc.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itqmbc.common.dao.UserDao;
import com.itqmbc.common.entityBean.UserBean;
import com.itqmbc.common.service.IUserTestServices;


@Service
public class userTestServicesImpl implements IUserTestServices {

	@Autowired
    private UserDao userDao;

	@Override
	public List<UserBean> findAll() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}
}
