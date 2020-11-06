package com.pjj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pjj.pojo.*;
import com.pjj.service.BackService;
import com.pjj.service.OrderService;
import com.pjj.util.Msg;
import com.pjj.util.OssFileUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("Back")
public class BackController {
    @Resource
    BackService backService;

    //去订单页面
    @GetMapping(value = "/goOrder")
    public String goOrder(HttpSession session, Model model) {
      Admin flag=(Admin)session.getAttribute("admin");
        System.out.println(flag+"houyyyyygsy");
        model.addAttribute("admin", flag);
        return "Order";
    }

    @ResponseBody
    @RequestMapping(value = "/Orderlist", method = RequestMethod.GET)
    public Msg Orderlist(@RequestParam(value = "pn", defaultValue = "1") Integer pn){
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Order> list = backService.BackSelectOrderAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(list, 5);
        return Msg.success().add("pageInfo", page);
    }


    //去订订单详情页面
    @GetMapping(value = "/goOrderDetail")
    public String goOrderDetail(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "Order_detail";
    }

    //订单详情
    @ResponseBody
    @RequestMapping(value = "/OrderDetail/{id}",method = RequestMethod.GET)
    public Msg OrderDetail(@PathVariable("id") Integer id,HttpSession session,Model model) {
        //个人订单查详情
        Order order=backService.BackselectByID(id);
        session.setAttribute("Order",order);
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        System.out.println(order);
        return Msg.success();
    }
    //订单详情
    @GetMapping(value = "/OrderDetails")
    @ResponseBody
    public Msg empUpdate(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        Order order=(Order) session.getAttribute("Order");
        return Msg.success().add("Detail", order);
    }

    //订单同意申请
    @ResponseBody
    @RequestMapping(value = "/OrderAgree/{id}",method = RequestMethod.POST)
    public Msg OrderAgree(@PathVariable("id") Integer id,@RequestParam("com") String com) {
        //处理后的订单数据保存
        backService.SavaDealOrder(id,com);
        return Msg.success();
    }

    //拒绝订单
    @ResponseBody
    @RequestMapping(value = "/OrderDelete/{id}",method = RequestMethod.POST)
    public Msg OrderDelete(@PathVariable("id") Integer id,@RequestParam("com") String com) {
        //处理后的订单数据保存
        backService.BackDeleteID(id,com);
        return Msg.success();
    }


    //去转让订单页面
    @GetMapping(value = "/goOrderHelp")
    public String goOrderHelp(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "Order_help";
    }
    //转让订单查询
    @ResponseBody
    @RequestMapping(value = "/OrderHelplist", method = RequestMethod.GET)
    public Msg OrderHelplist(@RequestParam(value = "pn", defaultValue = "1") Integer pn){
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Pet> list = backService.BackSelectHelpAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(list, 5);
        return Msg.success().add("pageInfo", page);
    }
    //去转让订单详情页面
    @GetMapping(value = "/goOrderHelpDetail")
    public String goOrderHelpDetail(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);

        return "Order_hdetail";
    }
    //去转让订单详情页面
    @ResponseBody
    @GetMapping(value = "/goOrderHelpDetails")
    public Msg goOrderHelpDetails(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        Pet pet=(Pet) session.getAttribute("Pet");
        System.out.println(pet+"hrkrhebdkgcfs");
        return Msg.success().add("Pet",pet);
    }
    //转让订单详情
    @ResponseBody
    @RequestMapping(value = "/OrderHelpDetail/{id}",method = RequestMethod.GET)
    public Msg OrderHelpDetail(@PathVariable("id") Integer id,HttpSession session) {
        //个人订单查详情
        Pet pet=backService.BackSelectHelpID(id);
        session.setAttribute("Pet",pet);
        System.out.println("1111111"+pet);
        return Msg.success();
    }
    //转让订单同意申请
    @ResponseBody
    @RequestMapping(value = "/OrderHelpAgree/{id}",method = RequestMethod.POST)
    public Msg OrderHelpAgree(@PathVariable("id") Integer id) {
        //处理后的订单数据保存
        backService.BackAgreeHelpDetal(id);
        return Msg.success();
    }

    //拒绝订单
    @ResponseBody
    @RequestMapping(value = "/OrderHelpDelete/{id}",method = RequestMethod.POST)
    public Msg OrderHelpDelete(@PathVariable("id") Integer id) {
        //处理后的订单数据保存
        backService.BackDisAgreeHelpDetal(id);
        return Msg.success();
    }

    //用户页面
    @GetMapping(value = "/goBackUser")
    public String goBackUser(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "user";
    }

    //用户查询
    @ResponseBody
    @RequestMapping(value = "/UserList", method = RequestMethod.GET)
    public Msg UserList(@RequestParam(value = "pn", defaultValue = "1") Integer pn){
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<User> list = backService.BackselectUserAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(list, 5);
        return Msg.success().add("pageInfo", page);
    }

    //用户信息页面
    @GetMapping(value = "/goUserDetail")
    public String goUserDetail(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "user_details";
    }

    //查看用户信息
    @ResponseBody
    @RequestMapping(value = "/UserLook/{id}",method = RequestMethod.GET)
    public Msg UserLook(@PathVariable("id") Integer id,HttpSession session){
        User user=backService.BackUserLook(id);
        session.setAttribute("User",user);
        return Msg.success();
    }

    //查看用户信息
    @ResponseBody
    @RequestMapping("/UserLookOne")
    public Msg UserLookOne(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
       User user=(User) session.getAttribute("User");
        return Msg.success().add("User",user);
    }

    //删除用户
    @ResponseBody
    @RequestMapping(value = "/UserDelete/{id}",method = RequestMethod.DELETE)
    public Msg UserDelete(@PathVariable("id") Integer id){
       backService.BackUserDel(id);
        return Msg.success();
    }

    //添加用户
    @ResponseBody
    @RequestMapping(value = "/UserAdd", method = RequestMethod.POST)
    public Msg UserAdd(@Valid User user, BindingResult result) {
        System.out.println("进入  emp  添加");
        System.out.println(user);
        if (result.hasErrors()) {
            //后端校验失败，
            //应该返回失败，在模态框中显示校验失败提示信息
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError f : errors) {
                System.out.println("错误的字段： " + f.getField());
                System.out.println("错误信息： " + f.getDefaultMessage());
                map.put(f.getField(), f.getDefaultMessage());
            }
            return Msg.fail().add("JSR303Error", map);
        } else {
            backService.BackUserAdd(user);
            System.out.println(user);
            return Msg.success().add("msg", "添加成功！！");
        }
    }

    //宠物查看全部
    //宠物页面
    @GetMapping(value = "/goBackGoods")
    public String goBackGoods(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "PetBack";
    }

    //查询
    @ResponseBody
    @RequestMapping(value = "/goBackGoodsList", method = RequestMethod.GET)
    public Msg goBackGoodsList(@RequestParam(value = "pn", defaultValue = "1") Integer pn){
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Goods> list = backService.BackSelectGoodsAll();
        System.out.println(list);
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(list, 5);
        return Msg.success().add("pageInfo", page);
    }

    //宠物信息详情页面
    @GetMapping(value = "/goBackGoodsDetail")
    public String goBackGoodsDetail(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "Pet_details";
    }

    //查看宠物信息详情
    @ResponseBody
    @RequestMapping(value = "/LookGoods/{id}",method = RequestMethod.GET)
    public Msg LookGoods(@PathVariable("id") Integer id,HttpSession session){
        Goods goods=backService.BackSelectGoodsOne(id);
        session.setAttribute("goods",goods);
        return Msg.success();
    }

    //查看宠物信息详情
    @ResponseBody
    @RequestMapping("/LookGoodsOne")
    public Msg LookGoodsOne(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        Goods goods= (Goods) session.getAttribute("goods");
        return Msg.success().add("goods",goods);
    }


    //宠物修改
    @GetMapping(value = "/goBackGoodsEdit")
    public String goBackGoodsEdit() {
        return "Pet_Edit";
    }

    //将宠物信息传给宠物修改页面
    @ResponseBody
    @RequestMapping(value = "/EditGoods",method = RequestMethod.GET)
    public Msg EditGoods(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        Goods goods= (Goods) session.getAttribute("goods");
        return Msg.success().add("goods",goods);
    }

    //查询出所有宠物类别
    @ResponseBody
    @RequestMapping(value = "/GoodsType",method = RequestMethod.GET)
    public Msg GoodsType(HttpSession session){
        List<Pettype> listtype=backService.BackSelectPettypeAll();
        return Msg.success().add("listtype",listtype);
    }

    //修改宠物信息
    //添加用户
    @ResponseBody
    @RequestMapping(value = "/GoodsChange", method = RequestMethod.PUT)
    public Msg GoodsChange(@Valid Goods goods) {
        System.out.println("进入  修改  添加");
        backService.BackGoodsChange(goods);
        System.out.println(goods);
        return Msg.success().add("msg", "修改成功！！");

    }


    //宠物搜索页面
    @GetMapping("/goBackPetSearch")
    public String goBackPetSearch(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "PetSeacher";
    }

    //宠物搜索
    @ResponseBody
    @RequestMapping(value = "/GoodsSeacher",method = RequestMethod.GET)
    public Msg GoodsSeacher(@Valid Goods goods,HttpSession session){
        System.out.println(goods);
        Goods gd=backService.BackGoodsSeacher(goods);
        session.setAttribute("goods",gd);
        return Msg.success().add("goods",gd);
    }


    //宠物类别的添加
    @ResponseBody
    @RequestMapping(value = "/InsertPettype",method = RequestMethod.POST)
    public Msg InsertPettype(@Valid Pettype pettype){
        System.out.println("宠物类别的添加");
        backService.BackInsertPettype(pettype);
        return Msg.success().add("msg", "添加成功");
    }


    //宠物的添加
    @RequestMapping(value = "/InsertPet",method = RequestMethod.POST)
    public String InsertPet(@Valid Goods goods, @RequestParam(value="file",required=false) MultipartFile file,
                         HttpServletRequest request, HttpSession session) throws Exception{
        if(!file.isEmpty()) {
            //获取上传的图片原始名称
            String fileSub = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            Random d = new Random();
            String img = UUID.randomUUID().toString().replace("-", "") + d.nextInt(10000) + "" + fileSub;
            try {
                // 生成文件名称

                String nameSuffix = "images"+"/" + img;    //上传原始图片到阿里云
                OssFileUtil ossFileUtil = new OssFileUtil();
                String uploadPath = ossFileUtil.uploadAliyun(file, nameSuffix);
                File f = new File("https:\\oss-cn-beijing.aliyuncs.com\\images");
                goods.setGdimg(uploadPath);
                if (!f.exists()) {
                    f.mkdirs();
                }
                file.transferTo(new File(f, uploadPath));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            backService.BackInsertPet(goods);
            return "redirect:goBackGoods";
        }else {
            return "redirect:goBackGoods";
        }

    }


    //新闻页面
    @GetMapping("/goBackNews")
    public String goBackNews(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "tag";
    }

    //新闻查询全部
    @ResponseBody
    @RequestMapping(value = "/goBackNewsList", method = RequestMethod.GET)
    public Msg goBackNewsList(@RequestParam(value = "pn", defaultValue = "1") Integer pn){
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<News> list = backService.BackSelectNewsAll();
        System.out.println(list);
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(list, 5);
        return Msg.success().add("pageInfo", page);
    }

    //新闻的删除
    @ResponseBody
    @RequestMapping(value = "/BackNewsDel/{id}", method = RequestMethod.DELETE)
    public Msg BackNewsDel(@PathVariable(value = "id") Integer id){
        System.out.println("11111111111111");
        backService.BackNewsDel(id);
        return Msg.success();
    }
    //新闻的ID查询
    @ResponseBody
    @RequestMapping(value = "/BackNewsByID", method = RequestMethod.GET)
    public Msg BackNewsByID(@RequestParam(value = "id") Integer id,HttpSession session){
        News news=backService.BackNewsByID(id);
        session.setAttribute("New",news);
        return Msg.success().add("News",news);
    }

    //新闻信息的修改
    @ResponseBody
    @RequestMapping(value = "/BackNewsUpdata", method = RequestMethod.PUT)
    public Msg BackNewsUpdata(@Valid News news){
        backService.BackNewsUpdata(news);
        System.out.println("11111111111111"+news);
        return Msg.success();
    }

    //新闻详情页面
    @GetMapping("/goBackNewsDetails")
    public String goBackNewsDetails(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "tag_details";
    }

    //新闻信息的查看
    @ResponseBody
    @RequestMapping(value = "/BackNewsDetail", method = RequestMethod.GET)
    public Msg BackNewsDetail(HttpSession session){
       News n =(News) session.getAttribute("New");
       return Msg.success().add("News",n);
    }

    //新闻的添加
    @ResponseBody
    @RequestMapping(value = "/BackNewsAdd", method = RequestMethod.POST)
    public Msg BackNewsAdd(@Valid News news){
        backService.BackNewsInsert(news);
        System.out.println("11111111111111"+news);
        return Msg.success();
    }
    //知识页面
    @GetMapping("/goBackContent")
    public String goBackContent(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "content";
    }

    //知识的查询全部
    @ResponseBody
    @RequestMapping(value = "/goBackContentList", method = RequestMethod.GET)
    public Msg goBackContentList(@RequestParam(value = "pn", defaultValue = "1") Integer pn){
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Content> list = backService.BackContentFindAll();
        System.out.println(list);
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(list, 5);
        return Msg.success().add("pageInfo", page);
    }

    //知识的删除
    @ResponseBody
    @RequestMapping(value = "/BackContentDel/{id}", method = RequestMethod.DELETE)
    public Msg BackContentDel(@PathVariable(value = "id") Integer id){
        System.out.println("11111111111111");
        backService.BackContentDel(id);
        return Msg.success();
    }
    //知识的ID查询
    @ResponseBody
    @RequestMapping(value = "/BackContentByID", method = RequestMethod.GET)
    public Msg BackContentByID(@RequestParam(value = "id") Integer id,HttpSession session){
        Content content=backService.BackContentByID(id);
        session.setAttribute("content",content);
        return Msg.success().add("content",content);
    }

    //知识信息的修改
    @ResponseBody
    @RequestMapping(value = "/BackContentUpdata", method = RequestMethod.PUT)
    public Msg BackContentUpdata(@Valid Content content){
        backService.BackContentUpdata(content);
        System.out.println("11111111111111"+content);
        return Msg.success();
    }

    //知识详情页面
    @GetMapping("/goBackContentDetails")
    public String goBackContentDetails(HttpSession session, Model model) {
        Admin flag=(Admin)session.getAttribute("admin");
        model.addAttribute("admin", flag);
        return "content_details";
    }

    //知识信息的查看
    @ResponseBody
    @RequestMapping(value = "/BackContentDetail", method = RequestMethod.GET)
    public Msg BackContentDetail(HttpSession session){
        Content n =(Content) session.getAttribute("content");
        return Msg.success().add("content",n);
    }

    //知识的添加
    @RequestMapping(value = "/BackContentAdd", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public String BackContentAdd(@Valid Content content,@RequestParam("file")MultipartFile file,HttpServletRequest request) throws IOException {
     //判断上传图片是否为空
       if(!file.isEmpty()) {
           //获取上传的图片原始名称
           String fileSub = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
           Random d = new Random();
           String img = UUID.randomUUID().toString().replace("-", "") + d.nextInt(10000) + "" + fileSub;
           try {
               // 生成文件名称
               String nameSuffix = "images"+"/" + img;    //上传原始图片到阿里云
               OssFileUtil ossFileUtil = new OssFileUtil();
               String uploadPath = ossFileUtil.uploadAliyun(file, nameSuffix);
               File f = new File("https:\\oss-cn-beijing.aliyuncs.com\\images");
               content.setCimg(uploadPath);
               if (!f.exists()) {
                   f.mkdirs();
               }
               file.transferTo(new File(f, uploadPath));
           } catch (IllegalStateException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
           System.out.println(content.getCimg());
           backService.BackContentInsert(content);
           return "redirect:goBackContent";
           }else {
           return "redirect:goBackContent";
       }

    }

}
