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

//忘记密码，密码重置，包括请求验证码、验证验证码、验证安全信息、重新设置密码
@WebServlet(name = "ForgetPwdController", value = "/forget")
public class ForgetPwdController extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject = JsonUtils.getRequestPostJson(request);
        HttpSession session = request.getSession();
        switch (jsonObject.getString("type")) {
            case "code":    //使用验证码重置密码
                String code = (String) session.getAttribute("vilificationCode");
                String username = (String) session.getAttribute("username_forget");
                String email = (String) session.getAttribute("email_forget");
                System.out.println("code:" + code);
                System.out.println("username:" + username);
                System.out.println("email:" + email);
                if (code == null || username == null || email == null) {
                    ResponseUtils.responseJson(400, "未获取验证码或验证码过期，请先获取验证码", response);
                } else {
                    if (email.equals(jsonObject.getString("email")) && code.equals(jsonObject.getString("code"))) {
                        ResponseUtils.responseJson(200, username, response);
                        session.removeAttribute("vilificationCode");
                        session.setAttribute("canReset", true);
                    } else {
                        ResponseUtils.responseJson(400, "验证码错误", response);
                    }
                }
                break;
            case "info":    //使用个人信息重置密码
                if (userService.isPersonalInfoRight(jsonObject.getString("username"), jsonObject.getString("email"), jsonObject.getString("phone"))) {
                    ResponseUtils.responseJson(200, jsonObject.getString("username"), response);
                    session.setAttribute("canReset", true);
                    session.setAttribute("username_forget", jsonObject.getString("username"));
                } else {
                    ResponseUtils.responseJson(400, "信息验证失败，请重新输入", response);
                }
                break;
            case "requestCode": //请求验证码
                JSONObject result = userService.getVerificationCode(jsonObject.getString("email"));
                if (result.getIntValue("state") == 0) {
                    session.setAttribute("username_forget", result.getString("username"));
                    session.setAttribute("email_forget", jsonObject.getString("email"));
                    session.setAttribute("vilificationCode", result.getString("code"));
                    session.setMaxInactiveInterval(60 * 5); //验证码有效期5分钟
                    ResponseUtils.responseJson(200, "验证码已发送", new JSONObject().put("username", result.getString("username")),
                            response);
                } else {
                    ResponseUtils.responseJson(400, "验证码发送失败", result, response);
                }
                break;
            case "password":    //修改密码
                String username_forget = (String) session.getAttribute("username_forget");
                if (session.getAttribute("canReset") != null && (Boolean) session.getAttribute("canReset") && username_forget.equals(jsonObject.getString("username"))) {
                    if (userService.changePassword(jsonObject.getString("username"), jsonObject.getString("password"))) {
                        ResponseUtils.responseJson(200, "密码修改成功", response);
                        session.removeAttribute("canReset");
                        session.removeAttribute("username_forget");
                        session.removeAttribute("email_forget");
                        session.removeAttribute("vilificationCode");
                        session.invalidate();
                    } else {
                        ResponseUtils.responseJson(400, "密码修改失败，请稍后再试", response);
                    }
                } else {
                    ResponseUtils.responseJson(400, "您尚未通过信息认证，请先认证后重置密码", response);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + jsonObject.getString("type"));
        }
    }
}
