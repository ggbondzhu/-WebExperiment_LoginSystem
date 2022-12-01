package controller;

import com.alibaba.fastjson.JSONObject;
import service.UserService;
import service.impl.UserServiceImpl;
import util.JsonUtils;
import util.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//用户注册
@WebServlet(name = "RegisterController", value = "/register")
public class RegisterController extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = JsonUtils.getRequestPostJson(req);
        //注册成功
        if (userService.register(jsonObject.getString("username"), jsonObject.getString("password"))) {
            ResponseUtils.responseJson(200, "注册成功", resp);
        } else {   //注册失败
            ResponseUtils.responseJson(400, "注册失败，可能是当前用户名已存在，请重试", resp);
            System.out.println("注册失败");
        }
    }
}
