package service.impl;

import com.alibaba.fastjson.JSONObject;
import dao.PersonalInfoDao;
import dao.UserDao;
import dao.impl.PersonalInfoDaoImpl;
import dao.impl.UserDaoImpl;
import domain.User;
import domain.UserCheck;
import domain.UserInfo;
import service.UserService;
import util.EmailUtils;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    PersonalInfoDao personalInfoDao = new PersonalInfoDaoImpl();

    @Override
    public UserCheck login(String username, String password) {
        UserCheck userCheck = userDao.findUserByNamePwd(username, password);
        if (userCheck == null) {
            userCheck = userDao.findUserByEmailPwd(username, password);
            if (userCheck == null) {
                userCheck = userDao.findUserByPhonePwd(username, password);
            }
        }
        return userCheck;
    }

    @Override
    public boolean register(String username, String password) {
        return userDao.insertNewUser(username, password);
    }

    @Override
    public boolean changePassword(String username, String password) {
        return userDao.changePassword(username, password);
    }

    @Override
    public boolean changePassword(String username, String password, String oldPassword) {
        if (userDao.findUserByNamePwd(username, oldPassword) != null) {
            return userDao.changePassword(username, password);
        }
        return false;
    }

    @Override
    public JSONObject getVerificationCode(String email) {
        UserCheck userCheck = userDao.findUserByEmail(email);
        JSONObject jsonObject = new JSONObject();
        if (userCheck != null) {
            String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
            if (EmailUtils.sendEmail(code, email)) {
                jsonObject.put("state", 0);
                jsonObject.put("code", code);
                jsonObject.put("username", userCheck.getUserName());
            } else {
                jsonObject.put("state", 1);
                jsonObject.put("message", "服务器未能成功发送邮件");
            }
        } else {
            jsonObject.put("state", 1);
            jsonObject.put("message", "该邮箱未绑定任何用户");
        }
        return jsonObject;
    }

    @Override
    public boolean isPersonalInfoRight(String username, String email, String phone) {
        UserCheck userCheck = userDao.findUserByNameEmailPhone(username, email, phone);
        if (userCheck != null)
            return true;
        return false;
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public List<UserInfo> findAllUserInfo(int page, int pageSize) {
        return personalInfoDao.findAllPersonalInfo((page - 1) * pageSize, pageSize);
    }

    @Override
    public int getUserInfoCount() {
        return personalInfoDao.getPersonalInfoCount();
    }

    @Override
    public List<UserInfo> findAllUserInfo() {
        return personalInfoDao.findAllPersonalInfo();
    }

    @Override
    public List<UserInfo> findUserInfoBySearch(String query) {
        return personalInfoDao.findPersonalInfoBySearch(query);
    }

    @Override
    public boolean deleteUser(String username) {
        return userDao.deleteUser(username);
    }

    @Override
    public boolean changeUserState(String username) {
        return userDao.changeUserState(username);
    }

    @Override
    public UserInfo getPersonalInfo(String username) {
        return personalInfoDao.findPersonalInfo(username);
    }

    @Override
    public boolean updatePersonalInfo(UserInfo userInfo, String username) {
        boolean flag = personalInfoDao.updatePersonalInfo(userInfo, username);
        boolean flag2 = userDao.updateUserPhoneEmail(userInfo, username);
        return flag && flag2;
    }
}
