package com.pjj.util;

import com.pjj.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*拦截器*/
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //进行判断是否进行过登录
        String uri = request.getRequestURI();
        if (uri.indexOf("Login/goLogin") > 0) {
            return true;
        }
        HttpSession session = request.getSession();
        User user_session = (User) session.getAttribute("user");
        System.out.println("拦截器管理进入");
        System.out.println(user_session);
        if (user_session != null) {
            return true;
        }
        //不符合条件，个弹出提示l
        request.setAttribute("msg","你还没有登录，请先登录！");
        //跳转到登录页面
        request.getRequestDispatcher("/WEB-INF/html/login.html").forward(request,response);
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
