package com.pjj.service;

import com.pjj.mapper.AdminMapper;
import com.pjj.mapper.UserMapper;
import com.pjj.pojo.Admin;
import com.pjj.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {
    @Resource
    AdminMapper adminMapper;
    @Resource
    UserMapper userMapper;
    //登录
    public User uLogin(User user){
        return userMapper.find(user);
    }
    //管理员登录
    public Admin aLogin(Admin admin){
        return adminMapper.find(admin);
    }
    //登录判断
    public int CheckLogin(User user){
        return  userMapper.findPhone(user);
    }

    public Boolean CheckResign(User user){
        return userMapper.insert(user);
    }
    //用户查询
    public User look(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }
    //用户修改信息-----用户修改密码
    public int update(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }



}
