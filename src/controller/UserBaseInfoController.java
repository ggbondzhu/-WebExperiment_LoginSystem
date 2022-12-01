package controller;

import com.alibaba.fastjson.JSONObject;
import domain.UserInfo;
import service.UserService;
import service.impl.UserServiceImpl;
import util.JsonUtils;
import util.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//获取用户基本信息，用户信息修改、用户密码修改
@WebServlet(name = "UserBaseInfoController", value = "/auth/userInfo")
public class UserBaseInfoController extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        //因为是登录后才能访问的，过滤器已经进行一次判断，所以不用判断是否为空
        ResponseUtils.responseJson(200, "获取个人信息成功", userService.getPersonalInfo(username), response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject = JsonUtils.getRequestPostJson(request);
        if (jsonObject == null) {
            ResponseUtils.responseJson(400, "请求参数错误", response);
            return;
        }
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (jsonObject.getString("type").equals("changePassword")) {
            String newPassword = jsonObject.getString("password");
            String oldPassword = jsonObject.getString("oldPassword");
            if (userService.changePassword(username, newPassword, oldPassword)) {
                ResponseUtils.responseJson(200, "密码修改成功", response);
            } else {
                ResponseUtils.responseJson(400, "密码修改失败，请检查原密码是否正确", response);
            }
        } else if (jsonObject.getString("type").equals("changeInfo")) {
            //System.out.println(jsonObject.getJSONObject("data"));
            UserInfo userInfo = jsonObject.getObject("data", UserInfo.class);
            if (userService.updatePersonalInfo(userInfo, username)) {
                ResponseUtils.responseJson(200, "个人信息修改成功", response);
            } else {
                ResponseUtils.responseJson(400, "个人信息修改失败，请检查格式是否正确", response);
            }
        }
    }
}
