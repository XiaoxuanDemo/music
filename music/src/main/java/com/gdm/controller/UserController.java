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
	 * 用户注册
	 * @param username 用户名
	 * @param password 密码
	 * @return 注册成功之后的用户信息
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
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 登录成功后返回登录成功的用户信息
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
	 * 修改用户头像
	 * @param userid 用户id
	 * @param protfile 头像地址
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
	 * 修改用户信息
	 * @param id 用户I D
	 * @param birthday 生日
	 * @param heart 心情
	 * @param address 地址
	 * @param sex 性别
	 * @param daxue 大学
	 * @return 修改成功后的用户信息
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
	 * 用户修改密码
	 * @param id 用户id
	 * @param oldpwd 旧密码
	 * @param newpwd 新密码
	 * @return 修改成功后的用户信息
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
				m.put(StaticContent.MSG, "用户未找到");
			}else{
				user.setNickname(nickname);
				userService.updateUser(user);
				m.put(StaticContent.MSG, "修改成功");
			}
		
		}else{
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
		}
		return m;
	}
	
}
