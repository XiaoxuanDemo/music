package com.gdm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdm.mapper.ListContentMapper;
import com.gdm.mapper.ListMapper;
import com.gdm.mapper.MusicMapper;
import com.gdm.model.ListContent;
import com.gdm.model.Music;
import com.gdm.model.MusicList;
import com.gdm.service.MusicService;
import com.github.pagehelper.PageHelper;

@Service("MusicService")
public class MusicServiceImpl implements MusicService{
	@Resource
	private MusicMapper musicdao;
	@Resource
	private ListMapper listdao;
	@Resource
	private ListContentMapper contentdao;
	@Override
	public Music addMuisc(Music m) {
		// TODO Auto-generated method stub
		int i = musicdao.insert(m);
		if(i>0){
			return m;
		}
		else{
			return null;
		}
	}
	@Override
	public List<Music> searchMusic(String keyword) {
		// TODO Auto-generated method stub
		return musicdao.searchMusicByname(keyword);
	}
	@Override
	public boolean addMusicList(MusicList m) {
		// TODO Auto-generated method stub
		int i = listdao.insert(m);
		if(i>0){
			return true;
		}
		return false;
	}
	@Override
	public List<ListContent> addMusic2MusicList(Integer musicid, Integer listid) {
		// TODO Auto-generated method stub
		int i = listdao.addMusic2List(musicid, listid);
		if(i>0){
			return contentdao.getMuiscList(listid);
		}
		return null;
	}
	@Override
	public List<ListContent> getListContentByListid(Integer listid) {
		// TODO Auto-generated method stub
		List<ListContent> list = contentdao.getMuiscList(listid);
		return list;
	}
	@Override
	public boolean deleteMusicList(Integer id) {
		// TODO Auto-generated method stub
		int i = listdao.deleteByPrimaryKey(id);
		if(i>0){
			return true;
		}
		return false;
	}
	@Override
	public boolean deleteMusicFromList(Integer musicid,Integer listid) {
		// TODO Auto-generated method stub
		int i = listdao.removeMusic2List(musicid, listid);
		if(i>0){
			return true;
		}
		return false;
	}
	@Override
	public List<MusicList> getListBytype(Integer type) {
		// TODO Auto-generated method stub
		return listdao.getListBytType(type);
	}
	@Override
	public List<MusicList> getListByuid(Integer uid) {
		// TODO Auto-generated method stub
		return listdao.getListByuserid(uid);
	}
	@Override
	public List<Music> getAllMusic(int pageSize,int pageNum) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageSize, pageNum);
		List<Music> all = musicdao.searchAll();
		return all;
	}
	
}
