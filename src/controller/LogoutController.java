package controller;

import util.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//用户退出登录
@WebServlet(name = "LogoutController", value = "/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseUtils.clearSessionCookie(req, resp);
//        HttpSession session = req.getSession();
//        session.removeAttribute("username");
//        session.removeAttribute("isAdmin");
//        session.invalidate();
//        Cookie cookie = new Cookie("JSESSIONID", null);
//        cookie.setMaxAge(0);
//        resp.addCookie(cookie);
//        System.out.println("用户已退出登录");
//        ResponseUtils.responseJson(200,"注销成功",resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
