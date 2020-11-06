package com.pjj.mapper;

import com.pjj.pojo.Pet;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PetMapper {

    int deleteByPrimaryKey(Integer pid);

    int insert(Pet record);

    int insertSelective(Pet record);
    //查询最新插入的数据
    Pet selectNew();
    //id查询
    Pet selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Pet record);

    int updateByPrimaryKey(Pet record);

    //后台查询全部订单
    List<Pet> BackSelectAll();
}