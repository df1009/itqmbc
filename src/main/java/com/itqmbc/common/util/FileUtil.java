package com.itqmbc.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;


public class FileUtil {

	public static void copyDir(String sourcePath, String newPath) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();

        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {
            if ((new File(sourcePath + File.separator + filePath[i])).isDirectory()) {
                copyDir(sourcePath  + File.separator  + filePath[i], newPath  + File.separator + filePath[i]);
            }

            if (new File(sourcePath  + File.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + File.separator + filePath[i], newPath + File.separator + filePath[i]);
            }

        }
    }

	public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);

        InputStream input = null;
        OutputStream output = null;
        try {
               input = new FileInputStream(oldFile);
               output = new FileOutputStream(file);
               byte[] buf = new byte[1024];
               int bytesRead;
               while ((bytesRead = input.read(buf)) > 0) {
                   output.write(buf, 0, bytesRead);
               }
        } finally {
            input.close();
            output.close();
        }


	}

	/**
     * 获取某个文件夹下的所有文件
     *
     * @param fileNameList 存放文件名称的list
     * @param path 文件夹的路径
     * @return
     */
    public static List<String> getAllFileName(String path) {
    	List<String> fileNameList = new ArrayList<String>();
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();
        if(tempList == null){
        	return null;
        }
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                fileNameList.add(tempList[i].getName());
            }
        }
        return fileNameList;
    }

    /**
     * 获取某个文件夹下的所有文件夹
     *
     * @param fileNameList 存放文件夹名称的list
     * @param path 文件夹的路径
     * @return
     */
    public static List<String> getAllFolderName(String path) {
    	System.out.println(path);
    	List<String> fileNameList = new ArrayList<String>();
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (!tempList[i].isFile()) {
                fileNameList.add(tempList[i].getName());
            }
        }
        return fileNameList;
    }


    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath,String newstr) throws IOException{
        Boolean bool = false;
        String filein = newstr+"\r\n";//新写入的行，换行
        String temp  = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            buffer.append(filein);
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    /**
     * 一行一行读取文件，解决读取中文字符时出现乱码
     *
     * 流的关闭顺序：先打开的后关，后打开的先关，
     *       否则有可能出现java.io.IOException: Stream closed异常
     *
     * @throws IOException
     */
    public static void readFileContent(String filepath) throws IOException {
        FileInputStream fis=new FileInputStream(filepath);
        InputStreamReader isr=new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        //简写如下
        //BufferedReader br = new BufferedReader(new InputStreamReader(
        //        new FileInputStream("E:/phsftp/evdokey/evdokey_201103221556.txt"), "UTF-8"));
        String line="";
        String[] arrs=null;
        while ((line=br.readLine())!=null) {
        	System.out.println(line);
        }
        br.close();
        isr.close();
        fis.close();
    }

    public static String readToString(String fileName)throws IOException {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        FileInputStream in = new FileInputStream(file);
        in.read(filecontent);
        in.close();
        return new String(filecontent, encoding);
    }


    /**
     * 新建controller写入的内容
     * @param packageName 包路径
     * @param methodName 方法名
     * @param parameterMap  入参{参数名:数据类型}
     * @param servicesReturnType service返回值类型
     * @return
     * @throws IOException
     */
    public static StringBuffer ImportPackage(String packageName,String servicesName,String servicesReturnType){
	   	 StringBuffer buffer = new StringBuffer();
	   	 buffer.append("package "+packageName+";");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import javax.servlet.http.HttpServletRequest;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import javax.servlet.http.HttpServletResponse;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import org.springframework.beans.factory.annotation.Autowired;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import org.springframework.stereotype.Controller;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import org.springframework.ui.Model;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import org.springframework.web.bind.annotation.RequestMapping;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import org.springframework.web.bind.annotation.RequestMethod;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import org.springframework.web.servlet.ModelAndView;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import java.util.Map;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import java.util.HashMap;");
	   	 buffer.append(System.getProperty("line.separator"));

	   	 buffer.append(System.getProperty("line.separator"));

	   	 buffer.append("import com.itqmbc.common.base.BaseController;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import com.itqmbc.common.constant.Constants;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import com.itqmbc.common.util.StringUtil;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import com.itqmbc.common.util.ConvUtils;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 buffer.append("import com.itqmbc.common.util.JsonUtil;");
	   	 buffer.append(System.getProperty("line.separator"));
	   	 if(servicesReturnType.contains("List")){
	   		buffer.append("import java.util.List;");
		   	buffer.append(System.getProperty("line.separator"));
	   	 }

	   	 buffer.append("import com.itqmbc.common.service."+servicesName+";");
	   	 buffer.append(System.getProperty("line.separator"));


	   	 buffer.append(System.getProperty("line.separator"));

	   	 return buffer;

    }


    /**
     * 新建controller写入的内容
     * @param packageName 包路径
     * @param controllerName controller类名
     * @param methodName 方法名
     * @param parameterMap  入参{参数名:数据类型}
     * @param servicesName services接口名
     * @param servicesReturnType service返回值类型
     * @return
     * @throws IOException
     */
    public static StringBuffer newControllerContent(String packageName,String controllerName,String methodName,JSONObject parameterMap,String servicesName,String servicesReturnType){
    	 StringBuffer buffer = new StringBuffer();
    	 //导包
    	 buffer.append(ImportPackage(packageName, servicesName,servicesReturnType));

    	 buffer.append("@Controller");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("public class "+controllerName+" extends BaseController{");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("@Autowired");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("private "+StringUtil.toUpperCaseFirstOne(servicesName)+" "+StringUtil.toLowerCaseFirstOne(servicesName)+";");
    	 buffer.append(System.getProperty("line.separator"));

    	 buffer.append(System.getProperty("line.separator"));

    	 buffer.append("	@RequestMapping(value = \"/loginOut/"+methodName+".do\", method ={ RequestMethod.POST,RequestMethod.GET})");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("	public ModelAndView "+methodName+"(HttpServletRequest request,HttpServletResponse response,Model model){");
    	 buffer.append(System.getProperty("line.separator"));

    	 buffer.append("		Map out = new HashMap();");
    	 buffer.append(System.getProperty("line.separator"));
    	 //接收页面参数
    	 for (Object key : parameterMap.keySet()){
    			 buffer.append("		String "+key+" = request.getParameter(\""+key+"\");");
    			 buffer.append(System.getProperty("line.separator"));
    		}
    	//验证页面参数
    	 int count = 0;
    	 buffer.append("		if(");
    	 for (Object key : parameterMap.keySet()){
    		 String dateType = parameterMap.getString((String) key);//参数类型
    		 if(count == 0){
    			 if("int".equals(parameterMap.getString((String) key))){
    				 buffer.append("!StringUtil.isNumber("+key+")");
    			 }else{
    				 buffer.append("StringUtil.isEmpty("+key+")");
    			 }
    		 }else{
    			 if("int".equals(parameterMap.getString((String) key))){
    				 buffer.append("			 ||!StringUtil.isNumber("+key+")");
    			 }else{
    				 buffer.append("			 ||StringUtil.isEmpty("+key+")");
    			 }

    		 }
        	 buffer.append(System.getProperty("line.separator"));
        	 count++;
    	 }
    	 buffer.append("			 ){");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("			 parameterError(response, out);");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("			 return null;");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append(" 		 }");
    	 buffer.append(System.getProperty("line.separator"));
    	 //封装请求参数
    	 buffer.append("		 Map reqMap = new HashMap();");
    	 buffer.append(System.getProperty("line.separator"));
    	 for (Object key : parameterMap.keySet()){
    		 buffer.append("		 reqMap.put(\""+key+"\", "+key+");");
        	 buffer.append(System.getProperty("line.separator"));
    	 }
    	 buffer.append("		"+servicesReturnType+" serviceReturn = "+StringUtil.toLowerCaseFirstOne(servicesName)+"."+methodName+"(reqMap);");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("		 out.put(Constants.RET_CODE, Constants.RET_SUCCESS_CODE);");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("		 out.put(Constants.RET_MSG, Constants.RET_SUCCESS_MSG);");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("		 JsonUtil.writeJson(response, out);");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("		 return null;");
    	 buffer.append(System.getProperty("line.separator"));
    	 buffer.append("	}");
    	 buffer.append(System.getProperty("line.separator"));
		 buffer.append("}");
		 buffer.append(System.getProperty("line.separator"));

    	 return buffer;
    }

    /**
     * 新建service接口写入的内容
     * @param packageName 包路径
     * @param methodName 方法名
     * @param servicesName services接口名
     * @param servicesReturnType service返回值类型
     * @return
     * @throws IOException
     */
    public static StringBuffer newServicesContent(String packageName,String methodName,String servicesName,String servicesReturnType){
    	StringBuffer buffer = new StringBuffer();
    	//头和导包
	   	buffer.append("package "+packageName+";");
	   	buffer.append(System.getProperty("line.separator"));
	   	if(servicesReturnType.contains("List")){
	   		buffer.append("import java.util.List;");
	   		buffer.append(System.getProperty("line.separator"));
	   	}
	   	buffer.append("import java.util.Map;");
   		buffer.append(System.getProperty("line.separator"));
	   	buffer.append(System.getProperty("line.separator"));
	   	buffer.append("public interface "+servicesName+" {");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("	"+servicesReturnType+" "+methodName+"(Map reqMap);");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("}");

		return buffer;

    }

    /**
     * 新建service接口写入的内容
     * @param packageName 包路径
     * @param methodName 方法名
     * @param servicesName services接口名
     * @param servicesImplName services实现类名
     * @param servicesReturnType service返回值类型
     * @return
     * @throws IOException
     */
    public static StringBuffer newServicesImplContent(String packageName,String methodName,String servicesName,String servicesImplName,String servicesReturnType){
    	StringBuffer buffer = new StringBuffer();
    	//头和导包
	   	buffer.append("package "+packageName+";");
	   	buffer.append(System.getProperty("line.separator"));
	   	if(servicesReturnType.contains("List")){
	   		buffer.append("import java.util.List;");
	   		buffer.append(System.getProperty("line.separator"));
	   	}
   		buffer.append("import java.util.Map;");
   		buffer.append(System.getProperty("line.separator"));
	   	buffer.append(System.getProperty("line.separator"));
	   	buffer.append("import org.springframework.beans.factory.annotation.Autowired;");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("import org.springframework.stereotype.Service;");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("import com.itqmbc.common.dao.TDao;");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append(System.getProperty("line.separator"));

   		buffer.append("import com.itqmbc.common.service."+servicesName+";");
   		buffer.append(System.getProperty("line.separator"));

	   	buffer.append(System.getProperty("line.separator"));

	   	buffer.append("@Service");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("public class "+servicesImplName+" implements "+servicesName+" {");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("	@Autowired");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("	private TDao tDao;");
   		buffer.append(System.getProperty("line.separator"));

   		buffer.append(System.getProperty("line.separator"));

   		buffer.append("	@Override");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("	public	"+servicesReturnType+" "+methodName+"(Map reqMap){");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("	return tDao."+methodName+"();");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("	}");
   		buffer.append(System.getProperty("line.separator"));
   		buffer.append("}");
   		buffer.append(System.getProperty("line.separator"));
		return buffer;
    }


}
