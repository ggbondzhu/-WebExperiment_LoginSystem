package util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResponseUtils {
    public static void responseJson(int code, String msg, Object data, HttpServletResponse resp) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        jsonObject.put("data", data);
        try {
            resp.setContentType("application/json");
            resp.getWriter().print(jsonObject);
            System.out.println("返回的json数据：" + jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void responseJson(int code, String msg, HttpServletResponse resp) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        try {
            resp.setContentType("application/json");
            resp.getWriter().print(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void responseByte(int code, String msg, byte[] data, HttpServletResponse resp) {
        try {
            resp.setContentType("image/png");
            resp.getOutputStream().write(data);
            //System.out.println("返回的byte数据：" + data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearSessionCookie(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.removeAttribute("isAdmin");
        session.invalidate();
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        System.out.println("用户已退出登录");
        ResponseUtils.responseJson(200, "注销成功", resp);
    }
}
