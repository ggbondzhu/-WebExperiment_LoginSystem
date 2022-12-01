package service;


import com.alibaba.fastjson.JSONObject;
import domain.User;
import domain.UserCheck;
import domain.UserInfo;

import java.util.List;

//该接口用于实现用户登录、注册、注销等功能
public interface UserService {
    UserCheck login(String username, String password);

    boolean register(String username, String password);

    boolean changePassword(String username, String password);

    boolean changePassword(String username, String password, String oldPassword);

    JSONObject getVerificationCode(String email);

    boolean isPersonalInfoRight(String username, String email, String phone);

    List<User> findAllUser();

    List<UserInfo> findAllUserInfo();

    List<UserInfo> findUserInfoBySearch(String query);

    boolean deleteUser(String username);

    boolean changeUserState(String username);

    UserInfo getPersonalInfo(String username);

    boolean updatePersonalInfo(UserInfo userInfo, String username);
}
