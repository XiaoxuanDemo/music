package com.gdm.tools;

public class StaticContent {
	public static final String MSG="message";
	public static final String USERISHAVE="�û��Ѿ�����";
	public static final String PARMISNULL="����Ϊ��";
	public static final String REGISTER_FAIL="ע��ʧ��";
	public static final String REGISTER_SUCCESS="ע��ɹ�";
	public static final String LOGIN_SUCCESS="��¼�ɹ�";
	public static final String PASSWORDISERROR="�������";
	public static final String FILEUPLOAD_FAIL="�ļ��ϴ�ʧ��";
	public static final String FILEUPLOAD_SUCCESS="�ļ��ϴ��ɹ�";
	public static final String UPDATE_SUCCESS="�޸ĳɹ�";
	public static final String UPDATE_FAIL="�޸�ʧ��";
	public static final String USERDONTHAVE="�û�������";
	public static final String UPLOADSUCCESS="�ϴ��ɹ�";
	public static final String UPLOADFAIL="�ϴ�ʧ��";
	public static final String SEARCH_SUCCESS="�����ɹ�";
	public static boolean parmsisNull(String parms){
		if(parms==null||parms.equals("")){
			return true;
		}else{
			return false;
		}
	}
}
