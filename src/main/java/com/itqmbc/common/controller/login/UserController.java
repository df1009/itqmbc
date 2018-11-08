package com.itqmbc.common.controller.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itqmbc.common.base.BaseController;
import com.itqmbc.common.constant.Constants;
import com.itqmbc.common.entityBean.UserBean;
import com.itqmbc.common.service.IUserTestServices;
import com.itqmbc.common.util.JsonUtil;

@Controller
public class UserController extends BaseController{
	Logger logger=Logger.getLogger(UserController.class);
	@Autowired
	private IUserTestServices userServices;
	//初始化产品列表
	@RequestMapping(value = "login/getUser.do", method ={ RequestMethod.POST,RequestMethod.GET})
	public ModelAndView getUser(HttpServletRequest request,HttpServletResponse response,Model model){
		Map out = new HashMap();
		logger.info("ProductController.initProductList  start");

		List<UserBean> list = userServices.findAll();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getMobile());
		}
		out.put(Constants.RET_CODE, Constants.RET_SUCCESS_CODE);
		out.put(Constants.RET_MSG, Constants.RET_SUCCESS_MSG);
		JsonUtil.writeJson(response,out);
		return null;
	}
}
