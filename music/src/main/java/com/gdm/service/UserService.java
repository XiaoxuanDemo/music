package com.gdm.service;

import com.gdm.model.User;

public interface UserService {
	User register(User u);
	/**
	 * ��ѯ�û�
	 * @param u �û�
	 * @param type 1.��ѯ�����û� �����û��� 
	 * @return
	 */
	User selectUser(String username);
	User selectUser(Integer id);
	User updateUser(User u);
}
