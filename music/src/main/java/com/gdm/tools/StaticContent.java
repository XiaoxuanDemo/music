package com.gdm.tools;

public class StaticContent {
	public static final String MSG="message";
	public static final String USERISHAVE="用户已经存在";
	public static final String PARMISNULL="参数为空";
	public static final String REGISTER_FAIL="注册失败";
	public static final String REGISTER_SUCCESS="注册成功";
	public static final String LOGIN_SUCCESS="登录成功";
	public static final String PASSWORDISERROR="密码错误";
	public static final String FILEUPLOAD_FAIL="文件上传失败";
	public static final String FILEUPLOAD_SUCCESS="文件上传成功";
	public static final String UPDATE_SUCCESS="修改成功";
	public static final String UPDATE_FAIL="修改失败";
	public static final String USERDONTHAVE="用户不存在";
	public static final String UPLOADSUCCESS="上传成功";
	public static final String UPLOADFAIL="上传失败";
	public static final String SEARCH_SUCCESS="搜索成功";
	public static boolean parmsisNull(String parms){
		if(parms==null||parms.equals("")){
			return true;
		}else{
			return false;
		}
	}
}
