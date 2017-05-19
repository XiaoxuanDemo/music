package com.gdm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdm.mapper.UserMapper;
import com.gdm.model.User;
import com.gdm.service.UserService;
@Service("UserService")
public class UserServiceImpl implements UserService{
	@Resource
	private UserMapper userdao;
	@Override
	public User register(User u) {
		// TODO Auto-generated method stub
		int i = userdao.insert(u);
		if(i>0){
			return userdao.getUser(u.getUsername());
		}else{
			return null;
		}
		
	}
	@Override
	public User selectUser(String username) {
		// TODO Auto-generated method stub
		
		return userdao.getUser(username);
		
	}
	@Override
	public User selectUser(Integer id) {
		// TODO Auto-generated method stub
		return userdao.selectByPrimaryKey(id);
	}
	@Override
	public User updateUser(User u) {
		// TODO Auto-generated method stub
		int i = userdao.updateByPrimaryKeySelective(u);
		if(i>0){
			return u;
		}else{
			return null;
		}
	}

}
