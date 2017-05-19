package com.gdm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gdm.model.ListContent;
import com.gdm.model.Music;
import com.gdm.model.MusicList;
import com.gdm.service.MusicService;
import com.gdm.tools.StaticContent;
import com.gdm.tools.UploadHelper;

@Controller
@RequestMapping("/music")
public class MusicController {
	@Resource
	private MusicService mservice;
	/**
	 * 上传音乐
	 * @param name 名字
	 * @param author 作家
	 * @param album 专辑
	 * @param musicfile 音乐文件
	 * @param mvfile mv文件
	 * @param lrcfile 歌词文件
	 * @param imgfile 图片文件
	 * @return 上传成功之后的音乐信息
	 */
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public Object uploadMusic(String name,String author,String album,@RequestParam("musicfile")MultipartFile musicfile,@RequestParam("mvfile")MultipartFile mvfile,@RequestParam("lrcfile")MultipartFile lrcfile,@RequestParam("imgfile")MultipartFile img){
		Map m=new HashMap();
		if(StaticContent.parmsisNull(name)||StaticContent.parmsisNull(album)||StaticContent.parmsisNull(author)||musicfile==null){
			m.put(StaticContent.MSG,StaticContent.PARMISNULL);
			return m;
		}
		UploadHelper helper = UploadHelper.getInstance();
		String musicpath = helper.saveMusic(musicfile);
		if(musicpath!=null){
			Music music = new Music();
			music.setSize("未知");
			music.setPath(musicpath);
			music.setName(name);
			music.setAuthor(author);
			music.setAlbum(album);
			if(mvfile!=null){
				String mvpath = helper.saveMVfile(mvfile);
				if(mvpath!=null){
					music.setMvpath(mvpath);
				}else{
					music.setMvpath("暂无");
				}
			}
			if(img!=null){
				String imgpath=helper.saveMusicImg(img);
				if(imgpath!=null){
					music.setImgpath(imgpath);
				}else{
					music.setImgpath("暂无");
				}
			}
			if(lrcfile!=null){
				String lrcpath=helper.saveLrc(lrcfile);
				if(lrcpath!=null){
					music.setLrcfile(lrcpath);
				}
				else {
					music.setLrcfile("暂无");
				}
			}
			music = mservice.addMuisc(music);
			if(music!=null){
				m.put("data", music);
				m.put(StaticContent.MSG, StaticContent.UPLOADSUCCESS);
				return m;
			}
		}
		m.put(StaticContent.MSG, StaticContent.UPLOADFAIL);	
		return m;
	}
	/**
	 * 搜索音乐
	 * @param keyword 关键字
	 * @return
	 */
	@RequestMapping(value="/search",method=RequestMethod.POST)
	@ResponseBody
	public Object musicSearch(String keyword){
		Map m=new HashMap();
		m.put(StaticContent.MSG,StaticContent.SEARCH_SUCCESS );
		if(StaticContent.parmsisNull(keyword)){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		List<Music> list = mservice.searchMusic(keyword);
		m.put("data", list);
		return m;
		
	}
	/**
	 * 添加音乐列表
	 * @param userid 用户id
	 * @param name 列表名
	 * @param discription 描述
	 * @param type 分类  1.表示用户喜欢，收藏等列表  2.表示系统自身的列表  3.表示用户创建的列表可以删除 其他列表均不可删除
	 * @param imgfile 列表封面
	 * @return
	 */
	@RequestMapping(value="/addmusiclist",method=RequestMethod.POST)
	@ResponseBody
	public Object addMusicList(Integer userid,String name,String discription,Integer type){
		Map m=new HashMap();
		if(StaticContent.parmsisNull(name)||StaticContent.parmsisNull(discription)||userid==null||type==null){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		MusicList list = new MusicList();
		list.setName(name);
		list.setUserid(userid);
		list.setDiscription(discription);
		list.setType(type);
		UploadHelper instance = UploadHelper.getInstance();
		list.setImgpath("");
		if(mservice.addMusicList(list)){
			m.put(StaticContent.MSG,"新增成功");
		}else{
			m.put(StaticContent.MSG, "新增失败");
		}
		return m;
	}
	/**
	 * 向音乐列表中添加音乐
	 * @param musicid 音乐id
	 * @param listid 列表id
	 * @return 添加成功后的列表
	 */
	@RequestMapping(value="/addMusic2list",method=RequestMethod.POST)
	@ResponseBody
	public Object addMusic2List(Integer musicid,Integer listid){
		Map m=new HashMap();
		if(musicid==null||listid==null){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		List<ListContent> list = mservice.addMusic2MusicList(musicid, listid);
		if(list==null){
			m.put(StaticContent.MSG, "添加失败");
		}else{
			m.put(StaticContent.MSG, "添加成功");
			m.put("data", list);
		}
		return m;
	}
	/**
	 * 查询列表中的音乐
	 * @param listid 列表的ID
	 * @return 查询成功后的结果列表
	 */
	@RequestMapping(value="/selectListContent",method=RequestMethod.GET)
	@ResponseBody
	public Object selectMusicListContent(Integer listid){
		Map m=new HashMap();
		if(listid==null){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		List<ListContent> list = mservice.getListContentByListid(listid);
		m.put("data", list);
		m.put(StaticContent.MSG, "查询成功");
		return m;
	}
	/**
	 * 删除音乐列表
	 * @param listid 列表ID
	 * @return
	 */
	@RequestMapping(value="/deleteMusicList",method=RequestMethod.POST)
	@ResponseBody
	public Object deleteMusicList(Integer listid){
		Map m=new HashMap();
		if(listid==null){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		if(mservice.deleteMusicList(listid)){
			m.put(StaticContent.MSG, "删除成功");
		}else{
			m.put(StaticContent.MSG, "删除失败");
		}
		return m;
		
	}
	public Object getMv(){
		Map m=new HashMap();
		return m;
	}
	/**
	 * 从音乐列表中删除音乐
	 * @param listid 列表id
	 * @param musicid音乐id
	 * @return
	 */
	@RequestMapping(value="/deleteMusicFromList",method=RequestMethod.POST)
	@ResponseBody
	public Object deleteMusicFromList(Integer listid,Integer musicid){
		Map m=new HashMap();
		if(listid==null||musicid==null){
			m.put(StaticContent.MSG, StaticContent.PARMISNULL);
			return m;
		}
		if(mservice.deleteMusicFromList(musicid, listid)){
			m.put(StaticContent.MSG, "删除成功");
		}else{
			m.put(StaticContent.MSG, "删除失败");
		}
		return m;
	}
	@RequestMapping(value="/getTypeList",method=RequestMethod.POST)
	@ResponseBody
	public Object getTypeList(Integer type,Integer userid){
		if(type!=null){
			Map m=new HashMap();
			List<MusicList> list = mservice.getListBytype(type);
			m.put(StaticContent.MSG, StaticContent.SEARCH_SUCCESS);
			m.put("data", list);
			return m;
		}else{
			Map m=new HashMap();
			List<MusicList> list = mservice.getListByuid(userid);
			m.put("data", list);
			m.put(StaticContent.MSG, "查询成功");
			return m;
		}
		
	}
	@RequestMapping(value="/getAllMusic",method=RequestMethod.GET)
	@ResponseBody
	public Object getAllMusic(Integer pageSize,Integer pageNum){
		Map m=new HashMap();
		List<Music> list = mservice.getAllMusic(pageSize,pageNum);
		m.put(StaticContent.MSG, "查询成功");
		m.put("data", list);
		return m;
	}
	@RequestMapping(value="/getPaiHang")
	@ResponseBody
	public Object getPaiHangBang(){
		Map m=new HashMap();
		ArrayList<Object> array = new ArrayList<Object>();
		List<MusicList> list = mservice.getListBytype(4);
		for(int i=0;i<list.size();i++){
			List<ListContent> listByuid = mservice.getListContentByListid(list.get(i).getId());
			array.add(listByuid);
		}
		m.put("data", array);
		m.put(StaticContent.MSG, StaticContent.SEARCH_SUCCESS);
		return m;
	}
}
