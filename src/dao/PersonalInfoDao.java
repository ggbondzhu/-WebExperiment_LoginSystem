package dao;

import domain.UserInfo;

import java.util.List;

public interface PersonalInfoDao {
    boolean updatePersonalInfo(UserInfo userInfo, String username);

    UserInfo findPersonalInfo(String username);

    List<UserInfo> findAllPersonalInfo();

    //分页查询
    List<UserInfo> findAllPersonalInfo(int start, int pageSize);

    int getPersonalInfoCount();

    List<UserInfo> findPersonalInfoBySearch(String query);
}
