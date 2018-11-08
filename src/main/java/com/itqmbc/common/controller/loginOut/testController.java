package com.itqmbc.common.controller.loginOut;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.itqmbc.common.base.BaseController;
import com.itqmbc.common.constant.Constants;
import com.itqmbc.common.util.StringUtil;
import com.itqmbc.common.service.IUserTestServices;
import com.itqmbc.common.util.FileUtil;
import com.itqmbc.common.util.JsonUtil;



@Controller
public class testController extends BaseController{
	@Autowired
	private IUserTestServices userServices;

	private String projectPath = "E:/workspace1/springmvc+mybatis/";
	private String projectControllerPath = "src/main/java/com/itqmbc/common/controller/";
	private String controllerPackagePath = "com.itqmbc.common.controller";
	private String projectServicesPath = "src/main/java/com/itqmbc/common/service/";
	private String servicesPackagePath = "com.itqmbc.common.service";

	//新建项目
	@RequestMapping(value = "loginOut/createProject.do", method ={ RequestMethod.POST,RequestMethod.GET})
	public ModelAndView createProject(HttpServletRequest request,HttpServletResponse response,Model model){
		Map out = new HashMap();
		logger.info("testController.createProject  start");
		try {
			FileUtil.copyDir("E:/itqmbc/base/springmvc+mybatis", projectPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.put(Constants.RET_CODE, Constants.RET_SUCCESS_CODE);
		out.put(Constants.RET_MSG, Constants.RET_SUCCESS_MSG);
		JsonUtil.writeJson(response,out);
		return null;
	}

	//查询Controller接口所在文件夹
	@RequestMapping(value = "loginOut/selControllerPath.do", method ={ RequestMethod.POST,RequestMethod.GET})
	public ModelAndView selControllerPath(HttpServletRequest request,HttpServletResponse response,Model model){
		Map out = new HashMap();
		logger.info("testController.selControllerPath  start");
		List<String> folderNameList  = null;
		folderNameList = FileUtil.getAllFolderName(projectPath+projectControllerPath);
		out.put("folderNameList", folderNameList);
		out.put(Constants.RET_CODE, Constants.RET_SUCCESS_CODE);
		out.put(Constants.RET_MSG, Constants.RET_SUCCESS_MSG);
		JsonUtil.writeJson(response,out);
		return null;
	}

	//查询Controller目录中的文件
	@RequestMapping(value = "loginOut/selConrollerFile.do", method ={ RequestMethod.POST,RequestMethod.GET})
	public ModelAndView selConrollerFile(HttpServletRequest request,HttpServletResponse response,Model model){
		Map out = new HashMap();
		logger.info("testController.selConrollerFile  start");
		String folderName = request.getParameter("folderName");
		if(StringUtil.isEmpty(folderName)){
			parameterError(response, out);
			return null;
		}
		List<String> fileNameList  = null;
		fileNameList = FileUtil.getAllFileName(projectPath+projectControllerPath+folderName);
		if(fileNameList == null){
			out.put(Constants.RET_CODE, "002");
			out.put(Constants.RET_MSG, "文件路径有误");
		}
		out.put("fileNameList", fileNameList);
		out.put(Constants.RET_CODE, Constants.RET_SUCCESS_CODE);
		out.put(Constants.RET_MSG, Constants.RET_SUCCESS_MSG);
		JsonUtil.writeJson(response,out);
		return null;
	}

	//Controller新建目录
	@RequestMapping(value = "loginOut/createConrollerPath.do", method ={ RequestMethod.POST,RequestMethod.GET})
	public ModelAndView createConrollerPath(HttpServletRequest request,HttpServletResponse response,Model model){
		Map out = new HashMap();
		logger.info("testController.createConrollerPath  start");
		String folderName = request.getParameter("folderName");
		File file=new File(projectPath+projectControllerPath+folderName);
		if(!file.exists()){//如果文件夹不存在
			file.mkdir();//创建文件夹
		}
		out.put(Constants.RET_CODE, Constants.RET_SUCCESS_CODE);
		out.put(Constants.RET_MSG, Constants.RET_SUCCESS_MSG);
		JsonUtil.writeJson(response,out);
		return null;
	}

	//新建Controller和services文件
	@RequestMapping(value = "loginOut/createFile.do", method ={ RequestMethod.POST,RequestMethod.GET})
	public ModelAndView createFile(HttpServletRequest request,HttpServletResponse response,Model model){
		System.out.println("111111111");
		Map out = new HashMap();
		logger.info("testController.createFile  start");
		String cFolderName = request.getParameter("CFolderName");//Controller所在文件夹名
		String cFileName = StringUtil.toUpperCaseFirstOne(request.getParameter("CFileName"));//Controller文件名

		String cMethodName = request.getParameter("CMethodName");//Controller接口方法名
		String isUserId = request.getParameter("isUserId");//用户id,1:需要作为参数  0:不需要作为参数
		//controller的入参
		String result = request.getParameter("result");//{参数名:参数类型}集合
		JSONObject obj = null;
		try {
			obj = JSONObject.fromObject(result);
		} catch (Exception e) {
			parameterError(response,out);
			return null;
		}
		Map cMap = new HashMap();
		Enumeration enu=request.getParameterNames();
		while(enu.hasMoreElements()){
			String paraName=(String)enu.nextElement();
			cMap.put(paraName, request.getParameter(paraName));
		}
		if("1".equals(isUserId))
			cMap.put("userId", isUserId);
		String serviceFileName = request.getParameter("SFileName");//services接口名
		String serviceImplFileName = serviceFileName;//services实现类名
		if(StringUtil.isEmpty(serviceFileName)){
			serviceFileName = cFileName+"Services";
			serviceImplFileName = serviceFileName+"Impl";
		}
		String returnDateType = request.getParameter("returnDateType");//返回值数据类型
		String isCollection = request.getParameter("isCollection");//返回值是否为集合1：是  0否
		String servicesReturnType = returnDateType;
		if("1".equals(isCollection))
				servicesReturnType = "List<"+returnDateType+">";
		String controllerPackageName = controllerPackagePath+"."+cFolderName;
		//controller文件
		String controllerFilenameTemp = projectPath+projectControllerPath+cFolderName+File.separator+cFileName+"Controller.java";//文件路径+名称+文件类型
		File file = new File(controllerFilenameTemp);
		if(!file.exists()){//如果文件不存在
			 try {
				file.createNewFile();//新建文件
				FileUtil.writeFileContent(controllerFilenameTemp, FileUtil.newControllerContent(controllerPackageName,cFileName+"Controller", cMethodName, obj, serviceFileName, servicesReturnType).toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{//修改controller文件

		}
		//services接口文件
		String serviceFilenameTemp = projectPath+projectServicesPath+serviceFileName+".java";//services接口文件路径+名称+文件类型
		File serviceFile = new File(serviceFilenameTemp);
		//service接口包
		String servicePackageName = servicesPackagePath;
		if(!serviceFile.exists()){//如果文件不存在
			 try {
				 serviceFile.createNewFile();//新建文件
				FileUtil.writeFileContent(serviceFilenameTemp, FileUtil.newServicesContent(servicePackageName,cMethodName,serviceFileName,servicesReturnType).toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{//修改services接口文件

		}

		//services实现类文件
		String serviceImplFilenameTemp = projectPath+projectServicesPath+"impl"+File.separator+serviceImplFileName+".java";//services接口文件路径+名称+文件类型
		File serviceImplFile = new File(serviceImplFilenameTemp);
		//service实现类包
		String serviceImplPackageName = servicesPackagePath+".impl";
		if(!serviceImplFile.exists()){//如果文件不存在
			 try {
				 serviceImplFile.createNewFile();//新建文件
				FileUtil.writeFileContent(serviceImplFilenameTemp, FileUtil.newServicesImplContent(serviceImplPackageName,cMethodName,serviceFileName,serviceImplFileName,servicesReturnType).toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{//修改services实现类文件

		}
		out.put(Constants.RET_CODE, Constants.RET_SUCCESS_CODE);
		out.put(Constants.RET_MSG, Constants.RET_SUCCESS_MSG);
		JsonUtil.writeJson(response,out);
		return null;
	}
}
