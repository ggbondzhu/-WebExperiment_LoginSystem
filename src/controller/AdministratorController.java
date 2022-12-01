package controller;

import com.alibaba.fastjson.JSONObject;
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
            JSONObject jsonObject = new JSONObject();
            List<UserInfo> data;
            if (query == null) {    //获取所有信息
                if (!request.getParameter("page").equals("undefined") && !request.getParameter("pageSize").equals("undefined")) {
                    int page = Integer.parseInt(request.getParameter("page"));
                    int pageSize = Integer.parseInt(request.getParameter("pageSize"));
                    data = userService.findAllUserInfo(page, pageSize);
                    jsonObject.put("result", data);
                    jsonObject.put("total", userService.getUserInfoCount());
                    jsonObject.put("page", page);
                } else {
                    data = userService.findAllUserInfo(1, 10);
                    jsonObject.put("result", data);
                    jsonObject.put("total", userService.getUserInfoCount());
                    jsonObject.put("page", 1);
                }
            } else {    //搜索
                data = userService.findUserInfoBySearch(query);
                jsonObject.put("result", data);
            }
            ResponseUtils.responseJson(200, "管理员获取所有用户信息", jsonObject, response);
        } else {
            ResponseUtils.responseJson(405, "非法操作，请登录后再试", response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
