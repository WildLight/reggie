package com.itheima.reggie.demo.filter;

import com.alibaba.fastjson.JSON;

import com.itheima.reggie.demo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器,支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取本次请求的路径
        String requestURI = request.getRequestURI();
        log.info("拦截到请求");
        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        boolean check = check(urls, requestURI);
        if (check) {
            log.info("本次的请求不需要处理{}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        //4、判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("已登录不需要拦截");
            //把获取到的用户ID存入到线程级中
            //Long byId = (Long) request.getSession().getAttribute("employee");

            filterChain.doFilter(request, response);
            return;
        }
        //判断,使用用户的状态,登录直接放行
        if (request.getSession().getAttribute("user") != null) {
            log.info("已登录不需要拦截",request.getSession().getAttribute("employee"));
            //把获取到的用户ID存入到线程级中
            //Long userId = (Long) request.getSession().getAttribute("user");

            filterChain.doFilter(request, response);
            return;
        }
        log.info("没有用户登录");
        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    private boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
