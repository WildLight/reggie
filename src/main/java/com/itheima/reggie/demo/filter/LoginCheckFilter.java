package com.itheima.reggie.demo.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.demo.common.BaseContext;
import com.itheima.reggie.demo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import javax.servlet.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器,支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取本次请求的路径
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}",requestURI);
        //判断此次请求是否需要拦截
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        //不需要拦截
        boolean check=check(requestURI,urls);
        if(check){
            log.info("请求：{}不需要拦截",requestURI);
            filterChain.doFilter(request,servletResponse);
            return;
        }
        if(request.getSession().getAttribute("employee")!=null){
            log.info("用户已经登录过，用户id：{}",request.getSession().getAttribute("employee"));
            Long empId=(Long) request.getSession().getAttribute("employee");
            BaseContext.setCreatId(empId);
            filterChain.doFilter(request,servletResponse);
            return;
        }
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    public boolean check(String requestURI,String[] urls){
        for(String url:urls){
            boolean match=PATH_MATCHER.match(url,requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }

}
