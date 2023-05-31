package com.lab3.interceptor;

import com.lab3.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class UserInterceptor implements HandlerInterceptor {

    @Override
    //原始方法调用前执行的内容
    //返回值类型可以拦截控制的执行，true放行，false终止
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws  Exception
    {
        Claims claims = JwtUtil.parse(request.getHeader("Authorization"));
        if (claims==null) return false;
        String identity=(String) claims.get("identity");
        int id=(int) claims.get("id");
        String token= JwtUtil.createJWT(request.getHeader("username"),id);
        response.setHeader("Authorization",token);
        response.setHeader("Access-Control-Allow-Origin","*");
        return identity.equals(request.getHeader("username")) && id>0;
    }
    @Override
    //原始方法调用后执行的内容
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");
    }

    @Override
    //原始方法调用完成后执行的内容
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }


}
