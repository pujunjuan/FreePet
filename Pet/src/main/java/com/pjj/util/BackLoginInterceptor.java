package com.pjj.util;

import com.pjj.pojo.Admin;
import com.pjj.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BackLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //进行判断是否进行过登录
        String uri = request.getRequestURI();
        if (uri.indexOf("Login/BackLogin") > 0) {
            return true;
        }
        HttpSession session = request.getSession();
        Admin user_session = (Admin) session.getAttribute("admin");
        System.out.println(session.getAttribute("admin"));
        if (user_session!= null) {
            return true;
        }
        //不符合条件，个弹出提示l
        request.setAttribute("msg","你还没有登录，请先登录！");
        //跳转到登录页面
        request.getRequestDispatcher("/WEB-INF/html/BackLogin.html").forward(request,response);
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }


}

