package com.itqmbc.common.base;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.itqmbc.common.constant.Constants;
import com.itqmbc.common.entityBean.UserBean;
import com.itqmbc.common.util.JsonUtil;


@Controller
public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected String getCookie(String cookieName) {
		Cookie[] cookie = getRequest().getCookies();
		for (int i = 0; i < cookie.length; i++) {
			Cookie cook = cookie[i];
			if(cook.getName().equalsIgnoreCase(cookieName)){ //获取键
				return cook.getValue();    //获取值
			}
		}
		return null;
	}
	protected HttpSession getSession() {
	    return getRequest().getSession();
	}

	protected HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}

	protected void setUser(UserBean user) {
		getSession().setAttribute(Constants.SESSION_USER_INFO, user);
	}

	protected UserBean getUser() {
		 return (UserBean) getSession().getAttribute(Constants.SESSION_USER_INFO);
	}

	protected void clearUserInfo() {
		getSession().removeAttribute(Constants.SESSION_USER_INFO);
	}

	protected String getEquipment(){
		//Enumeration   typestr = getRequest().getHeaderNames();
		String s1 = getRequest().getHeader("user-agent");
		if(s1.contains("Android")) {
			//System.out.println("Android移动客户端");
			return "android";
		} else if(s1.contains("iPhone")) {
			//System.out.println("iPhone移动客户端");
			return "iPhone";
		} else if(s1.contains("iPad")) {
			//System.out.println("iPad客户端");
			return "iPad";
		}  else {
			//System.out.println("其他客户端");
			return "other";
		}
	}

	/**
	 * 输出方法
	 * @param response
	 * @param out
	 * @param code
	 * @param value
	 * @return
	 */
	protected ModelAndView outparamMethod(HttpServletResponse response,
										  Map<String, Object> out, String code, String value) {
		out.put(Constants.RET_CODE, code);
		out.put(Constants.RET_MSG, value);
		JsonUtil.writeJson(response,out);
		return null;
	}

	/**
	 * 参数有误 输出方法
	 * @param response
	 * @param out
	 * @param code
	 * @param value
	 * @return  001  系统异常
	 */
	protected void parameterError(HttpServletResponse response,
										  Map<String, Object> out) {
		out.put(Constants.RET_CODE, "001");
		out.put(Constants.RET_MSG, "参有误");
		JsonUtil.writeJson(response,out);
	}
}
