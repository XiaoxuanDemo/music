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
	 * �ϴ�����
	 * @param name ����
	 * @param author ����
	 * @param album ר��
	 * @param musicfile �����ļ�
	 * @param mvfile mv�ļ�
	 * @param lrcfile ����ļ�
	 * @param imgfile ͼƬ�ļ�
	 * @return �ϴ��ɹ�֮���������Ϣ
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
			music.setSize("δ֪");
			music.setPath(musicpath);
			music.setName(name);
			music.setAuthor(author);
			music.setAlbum(album);
			if(mvfile!=null){
				String mvpath = helper.saveMVfile(mvfile);
				if(mvpath!=null){
					music.setMvpath(mvpath);
				}else{
					music.setMvpath("����");
				}
			}
			if(img!=null){
				String imgpath=helper.saveMusicImg(img);
				if(imgpath!=null){
					music.setImgpath(imgpath);
				}else{
					music.setImgpath("����");
				}
			}
			if(lrcfile!=null){
				String lrcpath=helper.saveLrc(lrcfile);
				if(lrcpath!=null){
					music.setLrcfile(lrcpath);
				}
				else {
					music.setLrcfile("����");
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
	 * ��������
	 * @param keyword �ؼ���
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
	 * ��������б�
	 * @param userid �û�id
	 * @param name �б���
	 * @param discription ����
	 * @param type ����  1.��ʾ�û�ϲ�����ղص��б�  2.��ʾϵͳ������б�  3.��ʾ�û��������б����ɾ�� �����б������ɾ��
	 * @param imgfile �б����
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
			m.put(StaticContent.MSG,"�����ɹ�");
		}else{
			m.put(StaticContent.MSG, "����ʧ��");
		}
		return m;
	}
	/**
	 * �������б����������
	 * @param musicid ����id
	 * @param listid �б�id
	 * @return ��ӳɹ�����б�
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
			m.put(StaticContent.MSG, "���ʧ��");
		}else{
			m.put(StaticContent.MSG, "��ӳɹ�");
			m.put("data", list);
		}
		return m;
	}
	/**
	 * ��ѯ�б��е�����
	 * @param listid �б��ID
	 * @return ��ѯ�ɹ���Ľ���б�
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
		m.put(StaticContent.MSG, "��ѯ�ɹ�");
		return m;
	}
	/**
	 * ɾ�������б�
	 * @param listid �б�ID
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
			m.put(StaticContent.MSG, "ɾ���ɹ�");
		}else{
			m.put(StaticContent.MSG, "ɾ��ʧ��");
		}
		return m;
		
	}
	public Object getMv(){
		Map m=new HashMap();
		return m;
	}
	/**
	 * �������б���ɾ������
	 * @param listid �б�id
	 * @param musicid����id
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
			m.put(StaticContent.MSG, "ɾ���ɹ�");
		}else{
			m.put(StaticContent.MSG, "ɾ��ʧ��");
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
			m.put(StaticContent.MSG, "��ѯ�ɹ�");
			return m;
		}
		
	}
	@RequestMapping(value="/getAllMusic",method=RequestMethod.GET)
	@ResponseBody
	public Object getAllMusic(Integer pageSize,Integer pageNum){
		Map m=new HashMap();
		List<Music> list = mservice.getAllMusic(pageSize,pageNum);
		m.put(StaticContent.MSG, "��ѯ�ɹ�");
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
