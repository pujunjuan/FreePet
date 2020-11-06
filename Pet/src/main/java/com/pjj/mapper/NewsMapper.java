package com.pjj.mapper;

import com.pjj.pojo.News;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewsMapper {

    int deleteByPrimaryKey(Integer nid);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer nid);

    //查询全部新闻
    List<News> FindNews();

   int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);
}