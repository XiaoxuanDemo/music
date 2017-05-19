package com.gdm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdm.model.MusicList;

public interface ListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MusicList record);

    int insertSelective(MusicList record);

    MusicList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MusicList record);

    int updateByPrimaryKey(MusicList record);
    int addMusic2List(@Param("musicid")Integer musicid,@Param("listid")Integer listid);
    int removeMusic2List(@Param("musicid")Integer musicid,@Param("listid")Integer listid);
    List<MusicList> getListBytType(Integer type);
    List<MusicList> getListByuserid(Integer userid);
}