package com.pjj.mapper;

import com.pjj.pojo.Transfer;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransferMapper {
    int deleteByPrimaryKey(Integer tid);

    int insert(Transfer record);

    int insertSelective(Transfer record);

    Transfer selectChangeTID(Integer id);

    List<Transfer> selectuKey(Integer id);

    int updateByPrimaryKeySelective(Transfer record);
    int updateByPID(Transfer record);
    int updateByPrimaryKey(Transfer record);
}