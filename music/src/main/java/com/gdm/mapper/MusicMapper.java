package com.gdm.mapper;

import java.util.List;

import com.gdm.model.Music;

public interface MusicMapper {
    int deleteByPrimaryKey(Integer musicid);

    int insert(Music record);

    int insertSelective(Music record);

    Music selectByPrimaryKey(Integer musicid);

    int updateByPrimaryKeySelective(Music record);

    int updateByPrimaryKey(Music record);
    
    List<Music> searchMusicByname(String name);
    List<Music> searchAll();
}