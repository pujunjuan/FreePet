package com.pjj.service;

import com.pjj.mapper.ContentMapper;
import com.pjj.mapper.GoodsMapper;
import com.pjj.mapper.NewsMapper;
import com.pjj.mapper.OrderMapper;
import com.pjj.pojo.Content;
import com.pjj.pojo.Goods;
import com.pjj.pojo.News;
import com.pjj.pojo.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class IndexService {
    @Resource
    NewsMapper newsMapper;
    @Resource
    ContentMapper contentMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Resource
    OrderMapper orderMapper;
    //查询所有新闻
    public List<News> FindNews(){
        return newsMapper.FindNews();
    }
    //查询所有知识
    public List<Content> FindContent(){
        return contentMapper.FindContent();
    }
    //查询所有宠物
    public List<Goods> FindGoods(){
        return goodsMapper.FindGoods();
    }
    //查询一个宠物
    public  Goods FindID(int id){
        return goodsMapper.selectByPrimaryKey(id);
    }
    //id查询新闻
    public  News FindNewsID(int id){
        return newsMapper.selectByPrimaryKey(id);
    }
    //id查询知识
    public  Content FindContentID(int id){
        return contentMapper.selectByPrimaryKey(id);
    }

    //申请领养宠物
    public int Apply(Order order, Integer id){
        order.setCollectiontime(new Date());
        order.setStutas("申请中");
        Goods goods=new Goods();
        goods.setGdstate("已申请");
        goods.setGdid(id);
        goodsMapper.updateByPrimaryKeySelective(goods);
        return orderMapper.insertSelective(order);
    }


}
