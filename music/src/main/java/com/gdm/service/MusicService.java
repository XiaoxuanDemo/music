package com.gdm.service;

import java.util.List;

import com.gdm.model.ListContent;
import com.gdm.model.Music;
import com.gdm.model.MusicList;

public interface MusicService {
	/**
	 * �������
	 * @param m
	 * @return ��ӳɹ����������Ϣ
	 */
	Music addMuisc(Music m);
	/**
	 * ��������
	 * @param keyword �ؼ���
	 * @return ������Ľ��
	 */
	List<Music> searchMusic(String keyword);
	/**
	 * ��������б�
	 * @param m �����б�
	 * @return �ɹ�Ϊtrue ʧ��Ϊfalse
	 */
	boolean addMusicList(MusicList m);
	/**
	 * ������ֵ��б���
	 * @param musicid ����id
	 * @param listid �б�id
	 * @return ��ӳɹ�����б�������
	 */
	List<ListContent> addMusic2MusicList(Integer musicid,Integer listid);
	/**
	 * ��ȡ�б��е�����
	 * @param listid �б��id
	 * @return ��ѯ�ɹ���������б���Ϣ
	 */
	List<ListContent> getListContentByListid(Integer listid);
	/**
	 * ɾ�������б�
	 * @param id �б��id
	 * @return �ɹ�����true ʧ�ܷ���false
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
