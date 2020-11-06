package com.pjj.mapper;

import com.pjj.pojo.Order;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
  //删除订单
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);
   /* <!--根据用户ID查询订单-->*/
   List<Order> selectByKey(Integer id);

   Order selectByID(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    //后台

    //查看所有申请订单
    List<Order> BackSelectAll();
//订单详情
    Order BackselectByID(Integer id);
}