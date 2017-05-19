package com.gdm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdm.mapper.CommentMapper;
import com.gdm.model.Comment;
import com.gdm.service.CommentService;
@Service("CommentService")
public class CommentServiceImpl implements CommentService {
	@Resource
	private CommentMapper dao;
	@Override
	public boolean addcomment(Comment c) {
		// TODO Auto-generated method stub
		int i = dao.insert(c);
		if(i>0){
			return true;
		}
		return false;
	}
	@Override
	public Comment getComment(Integer id) {
		// TODO Auto-generated method stub
		return dao.selectByPrimaryKey(id);
	}
	@Override
	public boolean updateComment(Comment c) {
		// TODO Auto-generated method stub
		if(dao.updateByPrimaryKeySelective(c)>0){
			return true;
		}
		return false;
	}

}
