package controller;

import domain.UserInfo;
import service.UserService;
import service.impl.UserServiceImpl;
import util.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//管理员请求用户信息
@WebServlet(name = "AdministratorController", value = "/auth/getAllUsers")
public class AdministratorController extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        HttpSession session = request.getSession();
        if (session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin")) {
            List<UserInfo> data;
            if (query == null) {
                data = userService.findAllUserInfo();
            } else {
                data = userService.findUserInfoBySearch(query);
            }
            ResponseUtils.responseJson(200, "管理员获取所有用户信息", data, response);
        } else {
            ResponseUtils.responseJson(405, "非法操作，请登录后再试", response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
