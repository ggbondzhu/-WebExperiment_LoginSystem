package controller;

import com.alibaba.fastjson.JSONObject;
import domain.UserCheck;
import service.UserService;
import service.impl.UserServiceImpl;
import util.JsonUtils;
import util.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

//用户登录
@WebServlet("/login")
public class LoginController extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post用户请求登录");
        JSONObject jsonObject = JsonUtils.getRequestPostJson(req);
        UserCheck userCheck = userService.login(jsonObject.getString("username"), jsonObject.getString("password"));
        //登录成功
        if (userCheck != null) {    //在数据库中查到数据
            if (userCheck.isEnable()) {   //账号可用
                HttpSession session = req.getSession();
                if (session.isNew() || session.getAttribute("username") == null) {    //判断是否是新的session或者为空的username
                    System.out.println("session创建成功");
                    session.setAttribute("username", userCheck.getUserName());
                    session.setAttribute("isAdmin", userCheck.isAdmin());
                    if (jsonObject.getString("remember").equals("true")) {
                        session.setMaxInactiveInterval(60 * 60 * 24 * 7);
                        Cookie cookie = new Cookie("JSESSIONID", req.getSession().getId());
                        cookie.setMaxAge(60 * 60 * 24 * 7);
                        resp.addCookie(cookie);
                        System.out.println("session设置为7天");
                    }
                } else {
                    System.out.println("session已存在，用户是：" + session.getAttribute("username"));
                }
                ResponseUtils.responseJson(200, "登录成功", userCheck, resp);
            } else {   //账号不可用
                ResponseUtils.responseJson(400, "登录失败，用户已注销或被管理员锁定", resp);
            }
        } else if (!req.getSession().isNew() && req.getSession().getAttribute("username") != null) {   //已经登录
            userCheck = new UserCheck();
            userCheck.setUserName((String) req.getSession().getAttribute("username"));
            userCheck.setEnable(true);
            userCheck.setAdmin((boolean) req.getSession().getAttribute("isAdmin"));
            ResponseUtils.responseJson(200, "用户已登录，正跳转至主页", userCheck, resp);
        } else {   //登录失败
            ResponseUtils.responseJson(401, "登录失败，用户名或密码错误", resp);
            System.out.println("用户密码错误");
        }
    }
}
