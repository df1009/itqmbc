package com.itqmbc.common.filter;


import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import com.itqmbc.common.constant.Constants;
import com.itqmbc.common.entityBean.UserBean;
import com.itqmbc.common.util.DateUtils;
import com.itqmbc.common.util.JsonUtil;

/**
 * 登录过滤
 *
 * @date 2017-3-20
 */
public class SessionFilter extends OncePerRequestFilter {
	Logger logger=Logger.getLogger(SessionFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	Map out = new HashMap();
    	System.out.println(DateUtils.convertDate2String(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
    	logger.info(DateUtils.convertDate2String(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
    	System.out.println("sessionId:"+request.getSession().getId());
    	logger.info("sessionId:"+request.getSession().getId());
    	System.out.println("ip:"+request.getRemoteAddr()+"   请求链接："+request.getRequestURI());
    	logger.info("ip:"+request.getRemoteAddr()+"   请求链接："+request.getRequestURI());
    	String ip = request.getHeader("x-forwarded-for");
		System.out.println(ip);
		logger.info(ip);
		Enumeration enu=request.getParameterNames();
		while(enu.hasMoreElements()){
			String paraName=(String)enu.nextElement();
			if(!"file".equals(paraName)){
				System.out.println(paraName+": "+request.getParameter(paraName));
				logger.info(paraName+": "+request.getParameter(paraName));
			}
		}
    	String s1 = request.getHeader("user-agent");
    	System.out.println("user:"+s1);
    	System.out.println("请求方式:"+request.getMethod());

    	logger.info("user:"+s1);
    	logger.info("请求方式:"+request.getMethod());
    	// 不过滤的uri
        //String[] notFilter = new String[] { "SCLogin/initIndex.do"};
    	String[] notFilter = new String[] { };
        // 请求的uri
        String uri = request.getRequestURI();
        // uri中包含SCLogin时才进行过滤
        if (uri.indexOf("/BCLogin/") != -1
        		&&!s1.equals("PostmanRuntime/6.3.2") ) {
            // 是否过滤
            boolean doFilter = true;
            for (String s : notFilter) {
                if (uri.indexOf(s) != -1) {
                    // 如果uri中包含不过滤的uri，则不进行过滤
                    doFilter = false;
                    break;
                }
            }
            if (doFilter) {
            	UserBean user =null;
            	//登录内PC端
    			user = (UserBean)request.getSession().getAttribute(Constants.SESSION_USER_INFO);
    			if (null == user) {
                	out.put(Constants.RET_CODE, "999");
        			out.put(Constants.RET_MSG, "未登录");
        			JsonUtil.writeJson(response,out);
                }else{
               	 filterChain.doFilter(request, response);
                }
    		}else{
           	 filterChain.doFilter(request, response);
            }
         }else{
        	 filterChain.doFilter(request, response);
         }
	}
}