package com.pjj.mapper;

import com.pjj.pojo.User;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
   //查询用户
    User find(User user);

    //通过属性检查用户是否存在
     int findPhone(User user);
//id删除
    int deleteByPrimaryKey(Integer uid);
   //添加用户
    Boolean insert(User record);
   //可以单一添加用户的属性
    int  insertSelective(User record);
    //id查询
    User selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    //后台
    //查看全部用户
    List<User> BackselectUserAll();
}