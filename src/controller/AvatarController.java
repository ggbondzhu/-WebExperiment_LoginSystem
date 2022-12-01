package controller;

import util.FileUtils;
import util.JsonUtils;
import util.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//用于请求用户头像、修改用户头像
@WebServlet(name = "AvatarController", value = "/auth/avatar")
public class AvatarController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        byte[] avatar = FileUtils.readFile(username);
        ResponseUtils.responseByte(200, "获取头像成功", avatar, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] requestPostBytes = JsonUtils.getRequestPostBytes(request);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String fileName = username;
        System.out.println(requestPostBytes);
        FileUtils.writeFile(requestPostBytes, fileName);
        ResponseUtils.responseJson(200, "修改头像成功", response);
    }
}
