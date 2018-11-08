package com.itqmbc.common.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

	public static boolean match(String regex, String str)
	  {
		if(isEmpty(str))
			return false;
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	  }

	 public static boolean isEmpty(String str)
	  {
	    return ((str == null) || (str.length() == 0));
	  }
	//是否为纯数字，带小数点和负号
	public static boolean isNumber(String value){
		if(isEmpty(value)){
			return false;
		}
        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        return value.matches(regex);
    }

	//是否为纯数字
	public static boolean isAllNumber(String value){
		if(isEmpty(value)){
			return false;
		}
        String regex = "[0-9]+";
        return value.matches(regex);
    }
	//手机号格式
	public static boolean isMobileNum(String str)
	  {
		if(isEmpty(str)){
			return false;
		}
	    String regex = "^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$";
	    return match(regex, str);
	  }
	//首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
