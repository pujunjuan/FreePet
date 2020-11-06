package com.pjj.mapper;

import com.pjj.pojo.Content;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentMapper {

    int deleteByPrimaryKey(Integer cid);

    int insert(Content record);

    int insertSelective(Content record);

    Content selectByPrimaryKey(Integer cid);

    List<Content> FindContent();

    int updateByPrimaryKeySelective(Content record);

    int updateByPrimaryKey(Content record);
}