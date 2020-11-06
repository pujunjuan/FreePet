package com.pjj.mapper;

import com.pjj.pojo.Goods;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsMapper {

    int deleteByPrimaryKey(Integer gdid);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods SelectGoods(Goods goods);

    //id查询
    Goods selectByPrimaryKey(Integer gdid);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    //宠物首页查询
    List<Goods> FindGoods();

    //后台查询
    List<Goods> BackSelectGoodsAll();

    //后台宠物的搜索
    Goods BackSelectGoods(Goods goods);

}