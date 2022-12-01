package dao;

import domain.User;
import domain.UserCheck;
import domain.UserInfo;

import java.util.List;

public interface UserDao {
    //用于验证用户合法性，用户登录等场景使用
    //boolean findUserByNamePwd(String username, String password);
    UserCheck findUserByNamePwd(String username, String password);

    UserCheck findUserByEmailPwd(String email, String password);

    UserCheck findUserByPhonePwd(String phone, String password);

    //用于注册新用户
    boolean insertNewUser(String username, String password);

    UserCheck findUserByNameEmailPhone(String username, String email, String phone);

    UserCheck findUserByEmail(String email);

    List<User> findAllUser();

    //用于修改密码
    boolean changePassword(String username, String password);

    boolean deleteUser(String username);

    boolean changeUserState(String username);

    boolean updateUserPhoneEmail(UserInfo userInfo, String username);
}
