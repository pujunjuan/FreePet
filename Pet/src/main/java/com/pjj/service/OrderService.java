package com.pjj.service;

import com.pjj.mapper.*;
import com.pjj.pojo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Resource
    OrderMapper orderMapper;
    @Resource
    DealOrderMapper dealOrderMapper;
    @Resource
    PettypeMapper pettypeMapper;
    @Resource
    PetMapper petMapper;
    @Resource
    TransferMapper transferMapper;
    @Resource
    GoodsMapper goodsMapper;
    //用户订单查询
    public List<Order>  FindOrderID(Integer id){
        return  orderMapper.selectByKey(id);
    }
    //订单查询详情
    public Order  selectByID(Integer id){
        return  orderMapper.selectByID(id);
    }
    //转让宠物信息查询
    public List<Transfer> selectuID(Integer id){
        return transferMapper.selectuKey(id);
    }
    //转让宠物信息查询详情
    public Pet selectChangeTID(Integer id){
        return petMapper.selectByPrimaryKey(id);
    }
    //转让宠物的提交
    public int insertpet(Pet pet,User uID){
        pet.setPtatus("申请中");
        pet.setPtime(new Date());
        petMapper.insertSelective(pet);
        Pet pet1=petMapper.selectNew();
        Transfer transfer=new Transfer();
        transfer.setPid(pet1.getPid());
        transfer.setUid(uID.getUid());
        return transferMapper.insertSelective(transfer);
    }

    //查询所有宠物种类
    public List<Pettype> selectAll(){
        return  pettypeMapper.selectAll();
    }
    //删除订单
    public int deleteOrder(Integer id){
       Order order= orderMapper.selectByID(id);
       Goods goods=new Goods();
       goods.setGdid(order.getGdid());
       goods.setGdstate("待领养");
       goodsMapper.updateByPrimaryKeySelective(goods);
       return orderMapper.deleteByPrimaryKey(id);
    }

    //删除转让信息
    public int deleteChangeOrder(Integer id){
        transferMapper.deleteByPrimaryKey(id);
        return petMapper.deleteByPrimaryKey(id);
    }

    //商家回复查询
    public DealOrder dealResult(Integer id){
        return dealOrderMapper.selectByPrimaryKey(id);
    }
}
