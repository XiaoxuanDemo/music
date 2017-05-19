package com.gdm.service;

import com.gdm.model.Comment;

public interface CommentService {
	boolean addcomment(Comment c);
	Comment getComment(Integer id);
	boolean updateComment(Comment c);
}
