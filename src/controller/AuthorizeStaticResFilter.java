package controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 对于需登录的静态页面（个人主页、管理员页面），直接重定向，进行过滤
//@WebFilter(filterName = "AuthorizeFilter", urlPatterns = "/auth/*")
public class AuthorizeStaticResFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        System.out.println("认证过滤器");
        if (session.getAttribute("username") == null) {
            System.out.println("用户未登录，拒绝访问，并重定向到登录页面");
            ((HttpServletResponse) servletResponse).sendRedirect("/WebLoginSystem/redirect/index.html?id=notLogin");
        } else {
            System.out.println("用户已登录，放行");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
