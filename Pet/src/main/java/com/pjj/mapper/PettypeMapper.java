package com.pjj.mapper;

import com.pjj.pojo.Pettype;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PettypeMapper {

    int deleteByPrimaryKey(Integer gid);

    int insert(Pettype record);

    int insertSelective(Pettype record);

    List<Pettype> selectAll();

    int selectByID(Pettype pettype);

    int updateByPrimaryKeySelective(Pettype record);

    int updateByPrimaryKey(Pettype record);
}