package com.pjj.service;

import com.pjj.mapper.*;
import com.pjj.pojo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BackService {
    //订单表
    @Resource
    OrderMapper orderMapper;

    @Resource
    DealOrderMapper dealOrderMapper;

    @Resource
    PetMapper petMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    PettypeMapper pettypeMapper;

    @Resource
    NewsMapper newsMapper;

    @Resource
    ContentMapper contentMapper;

    @Resource
    TransferMapper transferMapper;

    //查询所有申请的订单
    public List<Order> BackSelectOrderAll(){
        return orderMapper.BackSelectAll();
    }

    //查询所有申请的订单
    public Order BackselectByID(Integer id){
        return orderMapper.BackselectByID(id);
    }

    //处理订单保存的结果
    public int SavaDealOrder(int id,String com){
        //对订单状态进行修改
        Order order=new Order();
        order.setStutas("同意");
        order.setOid(id);
        orderMapper.updateByPrimaryKeySelective(order);
        //根据ID查询订单
        Order order1= orderMapper.selectByID(id);
        //订单信息插入订单处理表
        DealOrder dealOrder=new DealOrder();
        dealOrder.setOid(order1.getOid());
        dealOrder.setDealWay("通过");
        dealOrder.setDealResulst(order1.getStutas());
        dealOrder.setDealtime(new Date());
        dealOrder.setComment(com);
        return dealOrderMapper.insertSelective(dealOrder);
    }

    //拒绝订单
    public int  BackDeleteID(Integer id,String com){
        Order order=new Order();
        order.setStutas("拒绝");
        order.setOid(id);
        orderMapper.updateByPrimaryKeySelective(order);
        //根据ID查询订单
        Order order1= orderMapper.selectByID(id);
        //订单信息插入订单处理表
        DealOrder dealOrder=new DealOrder();
        dealOrder.setOid(order1.getOid());
        dealOrder.setDealWay("打回");
        dealOrder.setDealResulst(order1.getStutas());
        dealOrder.setDealtime(new Date());
        dealOrder.setComment(com);
        return dealOrderMapper.insertSelective(dealOrder);
    }

    //转让订单查询
    public List<Pet> BackSelectHelpAll(){
        return  petMapper.BackSelectAll();
    }

    //转让订单详情查询
    public Pet BackSelectHelpID(Integer id){
        return petMapper.selectByPrimaryKey(id);
    }

    //转让订单同意
    public int BackAgreeHelpDetal(Integer id){
        Pet petone=new Pet();
        petone.setPtatus("同意");
        petone.setPid(id);
        petMapper.updateByPrimaryKeySelective(petone);
        //根据id查询数据
        Pet pet=petMapper.selectByPrimaryKey(id);
        //插入goods表
        Goods goods=new Goods();
        //查询宠物类别ID
        Pettype pettype=new Pettype();
        pettype.setGtypename(pet.getPtype());
        int type=pettypeMapper.selectByID(pettype);
        System.out.println(type);

        goods.setGdsex(pet.getPsex());
        goods.setGdtype(type);
        goods.setGdname(pet.getPname());
        goods.setGdcontext(pet.getPcontent());
        goods.setGdtitle(pet.getPtitle());
        goods.setGdage(pet.getPage());
        goods.setGdstate("待领养");
        goods.setGdimg(pet.getPmanner());
        goods.setGdtime(new Date());
        goodsMapper.insert(goods);
        System.out.println(goods);

        Goods goods1=new Goods();
        goods1.setGdname(pet.getPname());
        goodsMapper.SelectGoods(goods1);


        //获取goods的ID插入trenfer
        Transfer transfer=new Transfer();
        transfer.setPid(pet.getPid());
        transfer.setGdid(goods1.getGdid());
        return transferMapper.updateByPID(transfer);
    }

    //转让订单拒绝
    public int BackDisAgreeHelpDetal(Integer id){
        Pet pet=new Pet();
        pet.setPtatus("拒绝");
        pet.setPid(id);
        return petMapper.updateByPrimaryKeySelective(pet);
    }

    //查看全部用户
     public List<User> BackselectUserAll(){
        return userMapper.BackselectUserAll();
    }

    //查看用户个人信息
    public User BackUserLook(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    //删除用户信息
    public int  BackUserDel(Integer id){
        return userMapper.deleteByPrimaryKey(id);
    }

    //添加用户
    public  int  BackUserAdd(User user){
        return userMapper.insertSelective(user);
    }

    //查看所有宠物
    public List<Goods> BackSelectGoodsAll(){
        return goodsMapper.BackSelectGoodsAll();
    }
    //宠物信息查看id查询
    public Goods BackSelectGoodsOne(Integer id){
        return goodsMapper.selectByPrimaryKey(id);
    }

    //查询出所有宠物类别
    public List<Pettype> BackSelectPettypeAll(){
        return pettypeMapper.selectAll();
    }

    //修改宠物信息
    public  int  BackGoodsChange(Goods goods){
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    //后台宠物的查询
    public Goods BackGoodsSeacher(Goods goods){
        return goodsMapper.BackSelectGoods(goods);
    }

    //宠物类别的添加
    public int BackInsertPettype(Pettype pettype){
        return pettypeMapper.insertSelective(pettype);
    }

    //宠物添加
    public int BackInsertPet(Goods goods){
        goods.setGdstate("待领养");
        goods.setGdtime(new Date());
        return goodsMapper.insertSelective(goods);
    }

    //新闻查询全部
    public List<News> BackSelectNewsAll(){
        return newsMapper.FindNews();
    }

    //删除新闻
    public int BackNewsDel(Integer id){
        return  newsMapper.deleteByPrimaryKey(id);
    }

    //id查询新闻
    public News BackNewsByID(Integer id){
        return newsMapper.selectByPrimaryKey(id);
    }

    //新闻的修改
    public int BackNewsUpdata(News news){
        return newsMapper.updateByPrimaryKeySelective(news);
    }

    //新闻的添加
    public int BackNewsInsert(News news){
        news.setNtime(new Date());
        return newsMapper.insertSelective(news);
    }

    //知识查询全部
    public List<Content> BackContentFindAll(){
        return contentMapper.FindContent();
    }

    //知识的删除
    public int BackContentDel(Integer id){
        return contentMapper.deleteByPrimaryKey(id);
    }

    //知识id查询
    public Content BackContentByID(Integer  id){
        return contentMapper.selectByPrimaryKey(id);
    }

    //知识的修改
    public int BackContentUpdata(Content content){
        return contentMapper.updateByPrimaryKeySelective(content);
    }
    //知识的添加
    public int BackContentInsert(Content content){
        content.setCtime(new Date());
        System.out.println(content.getCimg()+"tupianas");
        return contentMapper.insertSelective(content);
    }




}
