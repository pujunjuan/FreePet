package com.pjj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pjj.pojo.*;
import com.pjj.service.IndexService;
import com.pjj.service.LoginService;
import com.pjj.util.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("Index")
public class IndexController {
    @Resource
    IndexService indexService;
    @Resource
    LoginService loginService;

    @GetMapping("/goContent")
    public String goContent(){
        return "know";
    }

    @ResponseBody
    @RequestMapping(value = "/toContent", method = RequestMethod.GET)
    public Msg FindContent(@RequestParam(value = "pn", defaultValue = "1") Integer pn,Model model,HttpSession session){
        User emp= (User) session.getAttribute("user");
        model.addAttribute("user",emp);
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 6);
        List<Content> FindContent=indexService.FindContent();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(FindContent, 6);
        return Msg.success().add("content",page);
    }

    @GetMapping("/goNews")
    public String goNews(){
        return "news";
    }

    @ResponseBody
    @RequestMapping(value = "/toNews", method= RequestMethod.GET)
    public Msg FindNews(@RequestParam(value = "pn", defaultValue = "1") Integer pn,Model model,HttpSession session){
        User emp= (User) session.getAttribute("user");
        model.addAttribute("user",emp);
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 6);
        List<News> FindNews=indexService.FindNews();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(FindNews, 6);
        return Msg.success().add("news",page);
    }

    @GetMapping("/goPet")
    public String goPet(){
        return "Pet";
    }
    //根据id获取宠物信息.宠物页面
    @ResponseBody
    @RequestMapping(value = "/pet", method = RequestMethod.GET)
    public Msg getEmpById(@RequestParam(value = "pn", defaultValue = "1") Integer pn,Model model,HttpSession session) {
        User emp= (User) session.getAttribute("user");
        model.addAttribute("user",emp);
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn,6);
        List<Goods> FindGoods=indexService.FindGoods();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(FindGoods, 6);
        return Msg.success().add("Goods",page);
    }


    //查看宠物详情
    @RequestMapping(value = "/goPetDetail/{id}", method = RequestMethod.GET)
    public String goPetDetail(@PathVariable("id") Integer id, Model model,HttpSession session){
        Goods pet=indexService.FindID(id);
        User emp= (User) session.getAttribute("user");
        model.addAttribute("user",emp);
        session.setAttribute("pet",pet);
        model.addAttribute("pet",pet);
        return "PetDetails";
     }
    //查看新闻详情
    @RequestMapping(value = "/goNewsDetail/{id}", method = RequestMethod.GET)
    public String goNews(@PathVariable("id") Integer id, Model model,HttpSession session){
        News news=indexService.FindNewsID(id);
        User emp= (User) session.getAttribute("user");
        model.addAttribute("user",emp);
        session.setAttribute("news",news);
        model.addAttribute("news",news);
        return "NewsDetail";
    }
    //查看知识详情
    @RequestMapping(value = "/goConDeatil/{id}", method = RequestMethod.GET)
    public String goConDeatil(@PathVariable("id") Integer id, Model model,HttpSession session){
        Content content=indexService.FindContentID(id);
        User emp= (User) session.getAttribute("user");
        model.addAttribute("user",emp);
        session.setAttribute("content",content);
        model.addAttribute("content",content);
        return "KnowDetail";
    }

    //宠物申请领养
    @ResponseBody
    @RequestMapping(value = "/ApplyPet/{id}", method = RequestMethod.POST)
    public Msg ApplyPet(@PathVariable("id") Integer id, @RequestParam("addname") String addname,HttpSession session){
        User emp= (User) session.getAttribute("user");
        Order order=new Order();
        order.setGdid(id);
        order.setReason(addname);
        order.setUid(emp.getUid());
        order.setOcreater(emp.getUname());
        order.setOaddress(emp.getUaddress());
        indexService.Apply(order,id);
        return Msg.success();
    }




}
