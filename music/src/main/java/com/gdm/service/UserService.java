package com.gdm.service;

import com.gdm.model.User;

public interface UserService {
	User register(User u);
	/**
	 * 查询用户
	 * @param u 用户
	 * @param type 1.查询单个用户 根据用户名 
	 * @return
	 */
	User selectUser(String username);
	User selectUser(Integer id);
	User updateUser(User u);
}
