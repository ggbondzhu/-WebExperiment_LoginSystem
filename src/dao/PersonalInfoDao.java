package dao;

import domain.UserInfo;

import java.util.List;

public interface PersonalInfoDao {
    boolean updatePersonalInfo(UserInfo userInfo, String username);

    UserInfo findPersonalInfo(String username);

    List<UserInfo> findAllPersonalInfo();

    List<UserInfo> findPersonalInfoBySearch(String query);
}
