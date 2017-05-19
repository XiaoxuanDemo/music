package com.gdm.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gdm.model.User;
import com.gdm.service.UserService;
import com.gdm.tools.StaticContent;
import com.gdm.tools.UploadHelper;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;
	/**
	 * �û�ע��
	 * @param username �û���
	 * @param password ����
	 * @return ע��ɹ�֮����û���Ϣ
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public Object register(String username,String password){
		Map m=new HashMap();
		if(StaticContent.parmsisNull(username)||StaticContent.parmsisNull(password)){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		UUID uuid = UUID.randomUUID();
		User user = new User();
		User u = userService.selectUser(username);
		if(u!=null){
			m.put(StaticContent.MSG, StaticContent.USERISHAVE);
			return m;
		}
		user.setUsername(username);
		user.setPassword(password);
		user.setNickname(uuid.toString());
		User user2 = userService.register(user);
		if(user2!=null){
			m.put(StaticContent.MSG, StaticContent.REGISTER_SUCCESS);
			m.put("data", user2);
			return m;
		}else{
			m.put(StaticContent.MSG, StaticContent.REGISTER_FAIL);
			return m;
		}
		
	}
	/**
	 * �û���¼
	 * @param username �û���
	 * @param password ����
	 * @return ��¼�ɹ��󷵻ص�¼�ɹ����û���Ϣ
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Object login(String username,String password){
		Map m=new HashMap();
		if(StaticContent.parmsisNull(username)||StaticContent.parmsisNull(password)){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		User user = userService.selectUser(username);
		if(user.getPassword().equals(password)){
			m.put(StaticContent.MSG, StaticContent.LOGIN_SUCCESS);
			m.put("data",user);
			return m;
		}else{
			m.put(StaticContent.MSG, StaticContent.PASSWORDISERROR);
			return m;
		}
	}
	/**
	 * �޸��û�ͷ��
	 * @param userid �û�id
	 * @param protfile ͷ���ַ
	 * @return
	 */
	@RequestMapping(value="/uploadPort",method=RequestMethod.POST)
	@ResponseBody
	public Object upLoadPort(Integer userid,@RequestParam("portimg")MultipartFile protfile){
		Map m=new HashMap();
		UploadHelper instance = UploadHelper.getInstance();
		String path = instance.savePort(protfile);
		if(path==null){
			m.put(StaticContent.MSG, StaticContent.FILEUPLOAD_FAIL);
			return m;
		}
		User user = userService.selectUser(userid);
		user.setImgpath(path);
		user=userService.updateUser(user);
		if(user==null){
			m.put(StaticContent.MSG, StaticContent.FILEUPLOAD_FAIL);
			return m;
		}
		m.put(StaticContent.MSG, StaticContent.FILEUPLOAD_SUCCESS);
		m.put("data", user);
		return m;
	}
	/**
	 * �޸��û���Ϣ
	 * @param id �û�I D
	 * @param birthday ����
	 * @param heart ����
	 * @param address ��ַ
	 * @param sex �Ա�
	 * @param daxue ��ѧ
	 * @return �޸ĳɹ�����û���Ϣ
	 */
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	@ResponseBody
	public Object updateUserinfo(Integer id,String birthday,String heart,String address,String sex,String daxue,@RequestParam("background")MultipartFile bgfile){
		Map m=new HashMap();
		if(id==null){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		User u = new User();
		u.setId(id);
		User user = userService.selectUser(id);
		if(user!=null){
			if(!StaticContent.parmsisNull(birthday)){
				user.setBirthday(birthday);
			}
			if(!StaticContent.parmsisNull(heart)){
				user.setHeart(heart);
			}
			if(!StaticContent.parmsisNull(address)){
				user.setAddress(address);
			}
			if(!StaticContent.parmsisNull(sex)){
				user.setSex(sex);
			}
			if(!StaticContent.parmsisNull(daxue)){
				user.setDaxue(daxue);
			}
			UploadHelper instance = UploadHelper.getInstance();
			if(bgfile!=null){
				String ground = instance.saveBackGround(bgfile);
				if(ground!=null){
					user.setBackground(ground);
				}
			}
			user = userService.updateUser(user);
			if(user!=null){
				m.put(StaticContent.MSG, StaticContent.UPDATE_SUCCESS);
				m.put("data", user);
				return m;
			}else{
				m.put(StaticContent.MSG, StaticContent.UPDATE_FAIL);
				return m;
			}
		}
		m.put(StaticContent.MSG, StaticContent.UPDATE_FAIL);
		return m;
	}
	/**
	 * �û��޸�����
	 * @param id �û�id
	 * @param oldpwd ������
	 * @param newpwd ������
	 * @return �޸ĳɹ�����û���Ϣ
	 */
	@RequestMapping(value="/updatePassword",method=RequestMethod.POST)
	@ResponseBody
	public Object updatePassword(Integer id,String oldpwd,String newpwd){
		Map m=new HashMap();
		if(id==null||StaticContent.parmsisNull(oldpwd)||StaticContent.parmsisNull(newpwd)){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		User user = userService.selectUser(id);
		if(user!=null){
			if(user.getPassword().equals(oldpwd)){
				user.setPassword(newpwd);
				user = userService.updateUser(user);
				if(user!=null){
					m.put(StaticContent.MSG, StaticContent.UPDATE_SUCCESS);
					m.put("data",user);
					return m;
				}
				m.put(StaticContent.MSG, StaticContent.UPDATE_FAIL);
				return m;
			}else{
				m.put(StaticContent.MSG, StaticContent.PASSWORDISERROR);
				return m;
			}
		}
		m.put(StaticContent.MSG, StaticContent.USERDONTHAVE);
		return m;
	}
	@RequestMapping(value="/updateNickName",method=RequestMethod.POST)
	@ResponseBody
	public Object upDateNickName(Integer id,String nickname){
		Map m=new HashMap();
		if(!(StaticContent.parmsisNull(nickname)&&id!=null)){
			User user = userService.selectUser(id);
			if(user==null){
				m.put(StaticContent.MSG, "�û�δ�ҵ�");
			}else{
				user.setNickname(nickname);
				userService.updateUser(user);
				m.put(StaticContent.MSG, "�޸ĳɹ�");
			}
		
		}else{
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
		}
		return m;
	}
	
}
