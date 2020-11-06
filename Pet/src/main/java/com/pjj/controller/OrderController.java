package com.pjj.controller;

import com.pjj.pojo.*;
import com.pjj.service.LoginService;
import com.pjj.service.OrderService;
import com.pjj.util.OssFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@SuppressWarnings("ALL")
@RequestMapping("Order")
public class OrderController {
    @Resource
    LoginService loginService;
    @Resource
    OrderService orderService;

    //个人中心页面
    @RequestMapping(value = "/goPerson", method = RequestMethod.GET)
    public String goPerson(Model model, HttpSession session) {
        User emp = (User) session.getAttribute("user");
        model.addAttribute("user", emp);
        //个人订单查询
        List<Order> order = orderService.FindOrderID(emp.getUid());
        //个人信息查询,获取信息
        model.addAttribute("order", order);
        //宠物类别
        List<Pettype> ptype=orderService.selectAll();
        model.addAttribute("ptype", ptype);
        //转让信息
        List<Transfer> transfer=orderService.selectuID(emp.getUid());
        model.addAttribute("transfer", transfer);
        System.out.println(order);
        return "Person";
    }

    //删除订单
    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET)
    public String DeleteOrder(@PathVariable("id") Integer id) {
        orderService.deleteOrder(id);
        System.out.println("hhahhahhhahahah");
        return "redirect:/Order/goPerson";
    }
    //订单详情
    @RequestMapping(value = "/OrderDetail")
    public String OrderDetail(@RequestParam("id") Integer id, Model model, HttpSession session) {
        User emp = (User) session.getAttribute("user");
        model.addAttribute("user", emp);
        //个人订单查详情
        Order order = orderService.selectByID(id);
        model.addAttribute("order", order);
        //商家处理情况
        DealOrder dealOrder = orderService.dealResult(id);
        model.addAttribute("dealOrder", dealOrder);
        System.out.println(dealOrder);
        return "order_info";
    }
     //修改个人信息
     @RequestMapping(value = "/doupdate", method = RequestMethod.PUT)
     public String UpDateInfo(@RequestParam("Menbership") String Menbership,@RequestParam("ages") String ages,@RequestParam("phone") String phone,@RequestParam("address") String address, HttpSession session){
         User emp = (User) session.getAttribute("user");
         User user= new User();
         user.setUid(emp.getUid());
         user.setUaddress(address);
         user.setUmenbership(Menbership);
         user.setUphone(phone);
         user.setUage(ages);
        loginService.update(user);
         return "redirect:/Order/goPerson";
     }

    //用户修改密码
    //修改密码
    @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
    public String changepwd(@RequestParam("old") String old,@RequestParam("new1")String new1,@RequestParam("new1")String new2 ,HttpSession session) {
        User emp = (User) session.getAttribute("user");
        User user= new User();
        if(emp.getUpassoword().equals(old)){
            if(new1.equals(new2)){
                user.setUpassoword(new1);
                user.setUid(emp.getUid());
                loginService.update(user);
                return "redirect:/Login/goLogin";
            }
        }
        return "redirect:/Order/goPerson";
    }

    //转让订单详情
    @RequestMapping(value = "/ChangeDetail")
    public String ChangeDetail(@RequestParam("id") Integer id, Model model, HttpServletRequest request) {
        Pet pet=orderService.selectChangeTID(id);
        model.addAttribute("pet", pet);
        String filepath = request.getServletContext().getRealPath("/static/img");
        model.addAttribute("url", filepath);
        System.out.println(pet);
        return "order_helpinfo";
    }
    //删除转让信息
    @RequestMapping(value = "/DeleteChangeOrder/{id}", method = RequestMethod.GET)
    public String DeleteChangeOrder(@PathVariable("id") Integer id) {
        orderService.deleteChangeOrder(id);
        System.out.println("hhahhahhhahahah");
        return "redirect:/Order/goPerson";
    }
    //转让提交申请
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("title") String title,@RequestParam("name") String name,
                             @RequestParam("sex") String sex,@RequestParam("age") String age,
                             @RequestParam("type") String type,@RequestParam("product") String product,
                             @RequestParam(value="uploadFile",required=false) MultipartFile uploadFile,
                             HttpServletRequest request,HttpSession session)throws Exception{

        User emp = (User) session.getAttribute("user");
        Pet pet =new Pet();
        pet.setPtitle(title);
        pet.setPage(age);
        pet.setPsex(sex);
        pet.setPname(name);
        pet.setPcontent(product);
        pet.setPtype(type);

        if(!uploadFile.isEmpty()) {
            //获取上传的图片原始名称
            String fileSub = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
            Random d = new Random();
            String img = UUID.randomUUID().toString().replace("-", "") + d.nextInt(10000) + "" + fileSub;
            try {
                // 生成文件名称

                String nameSuffix = "images"+"/" + img;    //上传原始图片到阿里云
                OssFileUtil ossFileUtil = new OssFileUtil();
                String uploadPath = ossFileUtil.uploadAliyun(uploadFile, nameSuffix);
                File f = new File("https:\\oss-cn-beijing.aliyuncs.com\\images");
                pet.setPmanner(uploadPath);
                if (!f.exists()) {
                    f.mkdirs();
                }
                uploadFile.transferTo(new File(f, uploadPath));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            orderService.insertpet(pet,emp);
            return "redirect:/Order/goPerson";
        }else {
            return "redirect:/Order/goPerson";
        }
    }


}
