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
import javax.servlet.http.HttpSession;
import java.io.IOException;

//账号注销，包括：普通用户注销账号，是改变enable字段；管理员删除账号，是彻底删除；管理员启用/禁用账号，是改变enable字段
@WebServlet(name = "DeleteUserController", value = "/auth/delete")
public class DeleteUserController extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (username != null && !isAdmin) { // 用户已登录且不是管理员
            if (userService.changeUserState(username)) {
                ResponseUtils.clearSessionCookie(request, response);
            } else {
                ResponseUtils.responseJson(400, "账号注销失败，请稍后再试", response);
            }
        } else {
            ResponseUtils.responseJson(400, "非法操作，请登录后再试", response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        boolean isAdmin = (boolean) session.getAttribute("isAdmin");
        if (username != null && isAdmin) {   // 用户已登录且是管理员
            JSONObject jsonObject = JsonUtils.getRequestPostJson(request);
            String deleteUsername = jsonObject.getString("username");
            if (type.equals("delete")) {
                if (userService.deleteUser(deleteUsername)) {
                    ResponseUtils.responseJson(200, "账号已完全从数据库中删除", response);
                } else {
                    ResponseUtils.responseJson(400, "账号删除失败，请稍后再试", response);
                }
            } else if (type.equals("enable")) {
                if (userService.changeUserState(deleteUsername)) {
                    ResponseUtils.responseJson(200, "账号状态切换成功", response);
                } else {
                    ResponseUtils.responseJson(400, "切换账号状态失败，请稍后再试", response);
                }
            } else {
                ResponseUtils.responseJson(400, "参数错误", response);
            }

        } else {
            ResponseUtils.responseJson(400, "非法操作，请登录管理员账户后再试", response);
        }
    }
}
