package controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import domain.Educational;
import domain.Project;
import domain.Timeline;
import domain.UserInfo;
import service.EducationalService;
import service.ProjectService;
import service.TimelineService;
import service.UserService;
import service.impl.EducationalServiceImpl;
import service.impl.ProjectServiceImpl;
import service.impl.TimelineServiceImpl;
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
import java.util.List;

//获取用户基本信息，用户信息修改、用户密码修改
@WebServlet(name = "UserBaseInfoController", value = "/auth/userInfo")
public class UserBaseInfoController extends HttpServlet {
    UserService userService = new UserServiceImpl();
    TimelineService timelineService = new TimelineServiceImpl();
    ProjectService projectService = new ProjectServiceImpl();
    EducationalService educationalService = new EducationalServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get用户请求获取个人信息");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        UserInfo userInfo = userService.getPersonalInfo(username);
        List<Timeline> timeline = timelineService.findTimelineByUserID(userInfo.getUserID());
        List<Project> project = projectService.findProjectByUserID(userInfo.getUserID());
        List<Educational> educational = educationalService.findEducationalByUserID(userInfo.getUserID());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userInfo", userInfo);
        jsonObject.put("timeline", timeline);
        jsonObject.put("project", project);
        jsonObject.put("educational", educational);

        //因为是登录后才能访问的，过滤器已经进行一次判断，所以不用判断是否为空
        ResponseUtils.responseJson(200, "获取个人信息成功", jsonObject, response);
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
            UserInfo oldInfo = userService.getPersonalInfo(username);
            userInfo.setUserID(oldInfo.getUserID());
            // 判断data中是否有timeline_content
            if (jsonObject.getJSONObject("data").containsKey("timeline_content")) {
                JSONArray timeline = jsonObject.getJSONObject("data").getJSONArray("timeline_content");
                System.out.println(timeline);
                System.out.println(timeline);
                for (int i = 0; i < timeline.size(); i++) {
                    JSONObject timelineObject = timeline.getJSONObject(i);
                    timelineService.updateTimeline(timelineObject.getIntValue("id"), timelineObject.getString("content"),
                            timelineObject.getString("date"));
                }
            }
            // 判断data中是否有project_educational
            if (jsonObject.getJSONObject("data").containsKey("project_educational")) {
                JSONArray project_educational = jsonObject.getJSONObject("data").getJSONArray("project_educational");
                for (int i = 0; i < project_educational.size(); i++) {
                    JSONObject project_educationalObject = project_educational.getJSONObject(i);
                    JSONArray data = project_educationalObject.getJSONArray("data");
                    educationalService.updateEducational(project_educationalObject.getIntValue("id"),
                            project_educationalObject.getString("name"),
                            data.getString(0), data.getString(1), data.getString(2));
                }
            }
            // 判断data中是否有project_experience
            if (jsonObject.getJSONObject("data").containsKey("project_experience")) {
                JSONArray project_experience = jsonObject.getJSONObject("data").getJSONArray("project_experience");
                for (int i = 0; i < project_experience.size(); i++) {
                    JSONObject project_experienceObject = project_experience.getJSONObject(i);
                    JSONArray data = project_experienceObject.getJSONArray("data");
                    projectService.updateProject(project_experienceObject.getIntValue("id"),
                            project_experienceObject.getString("name"),
                            data.getString(0), data.getString(1), data.getString(2), data.getString(3));
                }
            }

            if (userService.updatePersonalInfo(userInfo, username)) {
                ResponseUtils.responseJson(200, "个人信息修改成功", response);
            } else {
                ResponseUtils.responseJson(400, "个人信息修改失败，请检查格式是否正确", response);
            }
        } else if (jsonObject.getString("type").equals("deleteTimeline")) {
            int id = jsonObject.getIntValue("id");
            if (timelineService.deleteTimeline(id)) {
                ResponseUtils.responseJson(200, "删除成功", response);
            } else {
                ResponseUtils.responseJson(400, "删除失败", response);
            }
        } else if (jsonObject.getString("type").equals("addTimeline")) {
            int userID = userService.getPersonalInfo(username).getUserID();
            if (timelineService.insertEmptyTimeline(userID)) {
                ResponseUtils.responseJson(200, "添加成功", response);
            } else {
                ResponseUtils.responseJson(400, "添加失败", response);
            }
        } else if (jsonObject.getString("type").equals("deleteProject")) {
            int id = jsonObject.getIntValue("id");
            if (projectService.deleteProject(id)) {
                ResponseUtils.responseJson(200, "删除成功", response);
            } else {
                ResponseUtils.responseJson(400, "删除失败", response);
            }
        } else if (jsonObject.getString("type").equals("addProject")) {
            int userID = userService.getPersonalInfo(username).getUserID();
            if (projectService.insertEmptyProject(userID)) {
                ResponseUtils.responseJson(200, "添加成功", response);
            } else {
                ResponseUtils.responseJson(400, "添加失败", response);
            }
        } else if (jsonObject.getString("type").equals("deleteEducational")) {
            int id = jsonObject.getIntValue("id");
            if (educationalService.deleteEducational(id)) {
                ResponseUtils.responseJson(200, "删除成功", response);
            } else {
                ResponseUtils.responseJson(400, "删除失败", response);
            }
        } else if (jsonObject.getString("type").equals("addEducational")) {
            int userID = userService.getPersonalInfo(username).getUserID();
            if (educationalService.insertEmptyEducational(userID)) {
                ResponseUtils.responseJson(200, "添加成功", response);
            } else {
                ResponseUtils.responseJson(400, "添加失败", response);
            }
        }

    }
}
