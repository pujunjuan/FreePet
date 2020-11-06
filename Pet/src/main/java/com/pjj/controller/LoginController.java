package com.pjj.controller;

import com.pjj.pojo.Admin;
import com.pjj.pojo.Goods;
import com.pjj.pojo.User;
import com.pjj.service.LoginService;
import com.pjj.util.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("Login")
public class LoginController {
    @Resource
    LoginService loginService;

    //去登录页面
    @GetMapping(value = "/goLogin")
    public String goLogin() {
        return "login";
    }

    //去主页
    @GetMapping(value = "/goIndex")
    public String goIndex( Model model, HttpSession session) {
        User emp= (User) session.getAttribute("user");
        model.addAttribute("user",emp);
        return "index";
    }

    //执行登录
    @RequestMapping(value = "/goLogin", method = RequestMethod.POST)
    public String goLogin(@RequestParam("phone") String phone, @RequestParam("password") String password, Model model, HttpSession session) {
        User user = new User();
        user.setUphone(phone);
        user.setUpassoword(password);
        System.out.println(111);
        User flag = loginService.uLogin(user);
        if (flag != null) {
            session.setAttribute("user", flag);
            model.addAttribute("user", flag);
            return "redirect:goIndex";
        }
        else {
            model.addAttribute("msg", "登陆失败，重新登录");
            return "login";
        }
    }

    //判断用户是否存在
    @ResponseBody
    @RequestMapping(value = "/checkphone", method = RequestMethod.POST)
    public Msg checkphone(@RequestParam("phone") String phone) {
        User user = new User();
        user.setUphone(phone);
        int flag = loginService.CheckLogin(user);
        if (flag == 0) {
            System.out.println(flag);
            return Msg.fail();
        } else {
            return Msg.success();
        }
    }


    //去注册页面
    @GetMapping(value = "/goResign")
    public String goResign() {
        return "registered";
    }

    //判断用户是否存在
    @ResponseBody
    @RequestMapping(value = "/checkp", method = RequestMethod.POST)
    public Msg checkp(@RequestParam("phone") String phone) {
        User user = new User();
        user.setUphone(phone);
        int flag = loginService.CheckLogin(user);
        if (flag != 0) {
            System.out.println(flag);
            return Msg.fail();
        } else {
            return Msg.success();
        }
    }

    //判断昵称是否重复
    @ResponseBody
    @RequestMapping(value = "/checkMenbership", method = RequestMethod.POST)
    public Msg checkMenbership(@RequestParam("Menbership") String Menbership) {
        User user = new User();
        user.setUmenbership(Menbership);
        int flag = loginService.CheckLogin(user);
        if (flag == 1) {
            System.out.println(flag);
            return Msg.fail();
        } else {
            return Msg.success();
        }
    }

    //执行注册
    @RequestMapping(value = "/goResign", method = RequestMethod.POST)
    public String goResign(@RequestParam("Menbership") @Valid String Menbership, @RequestParam("name") @Valid String name, @RequestParam("phone")  @Valid String phone, @RequestParam("password1") @Valid String password1, @RequestParam("password2") @Valid String password2, @RequestParam("sex") @Valid String sex, @RequestParam("age") @Valid String age, @RequestParam("address") @Valid String address, Model model, HttpSession session) {
        if ((password1.equals(password2))&&(Menbership!=""&&name!=""&&phone!=""&&sex!=""&&age!=""&&address!=""&&password1!=""&&password2!="")) {
            User user = new User();
            user.setUmenbership(Menbership);
            user.setUname(name);
            user.setUage(age);
            user.setUsex(sex);
            user.setUaddress(address);
            user.setUphone(phone);
            user.setUpassoword(password1);
            boolean flag = loginService.CheckResign(user);
            System.out.println(flag);
            if (flag == false) {
                System.out.println(flag);
                return "redirect:goLogin";
            } else {
                return "registered";
            }
        }
        return "registered";
    }

    //退出登录
    //退出页面
    @GetMapping(value = "/loginout")
    public String loginout(HttpSession session){
        //清除session
        session.invalidate();
        return "redirect:goLogin";
    }

    //退出登录
    //退出后台页面
    @GetMapping(value = "/Backloginout")
    public String Backloginout(HttpSession session){
        //清除session
        session.invalidate();
        return "redirect:BackLogin";
    }
    //后台管理员登录

    //去注册页面
    @GetMapping(value = "/BackLogin")
    public String BackLogin() {
        return "BackLogin";
    }

    //执行登录
    @RequestMapping(value = "/BackLogin", method = RequestMethod.POST)
    public String BackLogin(@RequestParam("aname") String aname, @RequestParam("password") String password, Model model, HttpSession session) {
        Admin admin=new Admin();
        admin.setAName(aname);
        admin.setAPassword(password);
        Admin flag=loginService.aLogin(admin);
        if (flag != null) {
            session.setAttribute("admin",flag);
            System.out.println(flag.getAName());
            model.addAttribute("admin", flag);
            return "redirect:/Back/goOrder";
        }
        else {
            model.addAttribute("msg", "登陆失败，重新登录");
            return "BackLogin";
        }
    }

}
