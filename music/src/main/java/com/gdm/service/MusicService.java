package com.gdm.service;

import java.util.List;

import com.gdm.model.ListContent;
import com.gdm.model.Music;
import com.gdm.model.MusicList;

public interface MusicService {
	/**
	 * 添加音乐
	 * @param m
	 * @return 添加成功后的音乐信息
	 */
	Music addMuisc(Music m);
	/**
	 * 搜索音乐
	 * @param keyword 关键字
	 * @return 搜索后的结果
	 */
	List<Music> searchMusic(String keyword);
	/**
	 * 添加音乐列表
	 * @param m 音乐列表
	 * @return 成功为true 失败为false
	 */
	boolean addMusicList(MusicList m);
	/**
	 * 添加音乐到列表中
	 * @param musicid 音乐id
	 * @param listid 列表id
	 * @return 添加成功后的列表总音乐
	 */
	List<ListContent> addMusic2MusicList(Integer musicid,Integer listid);
	/**
	 * 获取列表中的音乐
	 * @param listid 列表的id
	 * @return 查询成功后的音乐列表信息
	 */
	List<ListContent> getListContentByListid(Integer listid);
	/**
	 * 删除音乐列表
	 * @param id 列表的id
	 * @return 成功返回true 失败返回false
	 */
	boolean deleteMusicList(Integer id);
	/**
	 * 
	 * @param musicid
	 * @param listid
	 * @return
	 */
	boolean deleteMusicFromList(Integer musicid,Integer listid);
	List<MusicList> getListBytype(Integer type);
	List<MusicList> getListByuid(Integer uid);
	List<Music> getAllMusic(int pagesize,int nowpage);
}
