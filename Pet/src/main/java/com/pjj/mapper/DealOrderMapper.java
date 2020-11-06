package com.pjj.mapper;

import com.pjj.pojo.DealOrder;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DealOrderMapper {


    int deleteByPrimaryKey(Integer oiid);

    int insert(DealOrder record);

    int insertSelective(DealOrder record);

    DealOrder selectByPrimaryKey(Integer oid);

    int updateByPrimaryKeySelective(DealOrder record);

    int updateByPrimaryKey(DealOrder record);
}