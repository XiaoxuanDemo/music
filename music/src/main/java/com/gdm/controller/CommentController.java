package com.gdm.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdm.model.Comment;
import com.gdm.service.CommentService;
import com.gdm.tools.StaticContent;
@Controller
@RequestMapping(value="/comment")
public class CommentController {
	@Resource
	private CommentService service;
	/**
	 * 添加一条评论
	 * @param userid 用户id
	 * @param musicid 音乐id
	 * @param content 内容
	 * @return
	 */
	@RequestMapping(value="/commit",method=RequestMethod.POST)
	@ResponseBody
	public Object commitComment(Integer userid,Integer musicid,String content){
		Map m=new HashMap();
		
		if(StaticContent.parmsisNull(content)||userid==null||musicid==null){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		Comment c = new Comment();
		c.setUserid(userid);
		c.setMusicid(musicid);
		c.setContent(content);
		c.setCreatetime(System.currentTimeMillis()/1000+"");
		if(service.addcomment(c)){
			m.put(StaticContent.MSG, "评论成功");
		}else{
			m.put(StaticContent.MSG, "评论失败");
		}
		return m;
		
	}
	/**
	 * 点赞或者踩
	 * @param type 1.点赞 2.踩
	 * @param id 评论ID
	 * @return
	 */
	@RequestMapping(value="/good",method=RequestMethod.POST)
	@ResponseBody
	public Object changeType(Integer type,Integer id){
		Map m=new HashMap();
		Comment comment = service.getComment(id);
		if(comment!=null){
			if(type==1){
				Integer integer = comment.getGoodnum();
				if(integer==null){
					comment.setGoodnum(1);
				}else{
					comment.setGoodnum(integer+1);
				}
			}
			if(type==2){
				Integer integer = comment.getBadnum();
				if(integer==null){
					comment.setBadnum(1);
				}else{
					comment.setBadnum(integer+1);
				}
			}
			if(service.updateComment(comment)){
				if(type==1){
					m.put(StaticContent.MSG, "点赞成功");
				}else if(type==2){
					m.put(StaticContent.MSG, "踩成功");
				}
				
			}
			
		}else{
			m.put(StaticContent.MSG, "未找到评论");
		}
		return m;
	}
}
