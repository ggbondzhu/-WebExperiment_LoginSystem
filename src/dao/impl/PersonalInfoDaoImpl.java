package dao.impl;

import dao.PersonalInfoDao;
import domain.UserInfo;
import util.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonalInfoDaoImpl implements PersonalInfoDao {
    //更新用户信息，只更新有值的那部分
    @Override
    public boolean  updatePersonalInfo(UserInfo userInfo, String username) {
        String sql = "UPDATE `loginsystem`.`personal_info` SET `gender` = IFNULL(?,`personal_info`.gender), " +
                " `school` = IFNULL(?,`personal_info`.school), `college` = IFNULL(?,`personal_info`.college)," +
                " `major` = IFNULL(?,`personal_info`.major), `sno` = IFNULL(?,`personal_info`.sno), `native_place` = IFNULL(?,`personal_info`.native_place)," +
                "`age` = IF(?,?,`personal_info`.age), `project_num` = IF(?,?,`personal_info`.project_num), `fans_num` = IF(?,?,`personal_info`.fans_num), `asset_num` = IF(?,?,`personal_info`.asset_num)," +
                " `about` = IFNULL(?,`personal_info`.about), `avatar_url` = IFNULL(?,`personal_info`.avatar_url),`name` = IFNULL(?,`personal_info`.name) WHERE `user_id` = (SELECT user_id from user WHERE user_name = ?);";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setBoolean(1, userInfo.isGender());
            ps.setString(2, userInfo.getSchool());
            ps.setString(3, userInfo.getCollege());
            ps.setString(4, userInfo.getMajor());
            ps.setString(5, userInfo.getSno());
            ps.setString(6, userInfo.getNativePlace());
            ps.setInt(7, userInfo.getAge());
            ps.setInt(8, userInfo.getAge());
            ps.setInt(9, userInfo.getProjectNum());
            ps.setInt(10, userInfo.getProjectNum());
            ps.setInt(11, userInfo.getFansNum());
            ps.setInt(12, userInfo.getFansNum());
            ps.setInt(13, userInfo.getAssetNum());
            ps.setInt(14, userInfo.getAssetNum());
            ps.setString(15, userInfo.getAbout());
            ps.setString(16, userInfo.getAvatarUrl());
            ps.setString(17, userInfo.getName());
            ps.setString(18, username);
            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserInfo findPersonalInfo(String username) {
        String sql = "select * from personal_info join user u on u.user_id = personal_info.user_id where user_name = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            UserInfo userInfo = new UserInfo();
            while (resultSet.next()) {
                //userInfo.setUserID(resultSet.getInt("user_id"));
                userInfo.setNativePlace(resultSet.getString("native_place"));
                userInfo.setAge(resultSet.getInt("age"));
                userInfo.setProjectNum(resultSet.getInt("project_num"));
                userInfo.setFansNum(resultSet.getInt("fans_num"));
                userInfo.setAssetNum(resultSet.getInt("asset_num"));
                userInfo.setAbout(resultSet.getString("about"));
                //userInfo.setAvatarUrl(resultSet.getString("avatar_url"));
                userInfo.setCollege(resultSet.getString("college"));
                userInfo.setSno(resultSet.getString("sno"));
                userInfo.setMajor(resultSet.getString("major"));
                userInfo.setSchool(resultSet.getString("school"));
                userInfo.setName(resultSet.getString("name"));
                userInfo.setGender(resultSet.getBoolean("gender"));
                userInfo.setPhone(resultSet.getString("phone"));
                userInfo.setEmail(resultSet.getString("email"));
            }
            return userInfo;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getPersonalInfoCount() {
        String sql = "select count(*) from personal_info";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                //userInfo.setUserID(resultSet.getInt("user_id"));
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<UserInfo> findAllPersonalInfo() {
        String sql = "select * from personal_info join user u on u.user_id = personal_info.user_id order by u.user_id";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            List<UserInfo> userInfoList = new ArrayList<>();
            while (resultSet.next()) {
                //userInfo.setUserID(resultSet.getInt("user_id"));
                UserInfo userInfo = new UserInfo();
                userInfo.setNativePlace(resultSet.getString("native_place"));
                userInfo.setAge(resultSet.getInt("age"));
                userInfo.setProjectNum(resultSet.getInt("project_num"));
                userInfo.setFansNum(resultSet.getInt("fans_num"));
                userInfo.setAssetNum(resultSet.getInt("asset_num"));
                userInfo.setAbout(resultSet.getString("about"));
                //userInfo.setAvatarUrl(resultSet.getString("avatar_url"));
                userInfo.setCollege(resultSet.getString("college"));
                userInfo.setSno(resultSet.getString("sno"));
                userInfo.setMajor(resultSet.getString("major"));
                userInfo.setSchool(resultSet.getString("school"));
                userInfo.setName(resultSet.getString("name"));
                userInfo.setGender(resultSet.getBoolean("gender"));
                userInfo.setPhone(resultSet.getString("phone"));
                userInfo.setEmail(resultSet.getString("email"));
                userInfo.setCreateTime(resultSet.getTimestamp("create_time"));
                userInfo.setUserName(resultSet.getString("user_name"));
                userInfo.setUserID(resultSet.getInt("user_id"));
                userInfo.setAdmin(resultSet.getBoolean("is_admin"));
                userInfo.setEnable(resultSet.getBoolean("enable"));
                userInfoList.add(userInfo);
            }
            return userInfoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserInfo> findAllPersonalInfo(int start, int pageSize) {
//        String sql = "select * from personal_info join user u on u.user_id = personal_info.user_id order by u.user_id";
        String sql = "SELECT * FROM (personal_info join user u on u.user_id = personal_info.user_id) ORDER BY u.user_id LIMIT ?,?;";
        //SELECT * FROM tablename WHERE 查询条件 ORDER BY 排序条件 LIMIT ((页码-1)*页大小),页大小;
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, start);
            ps.setInt(2, pageSize);
            ResultSet resultSet = ps.executeQuery();
            List<UserInfo> userInfoList = new ArrayList<>();
            while (resultSet.next()) {
                //userInfo.setUserID(resultSet.getInt("user_id"));
                UserInfo userInfo = new UserInfo();
                userInfo.setNativePlace(resultSet.getString("native_place"));
                userInfo.setAge(resultSet.getInt("age"));
                userInfo.setProjectNum(resultSet.getInt("project_num"));
                userInfo.setFansNum(resultSet.getInt("fans_num"));
                userInfo.setAssetNum(resultSet.getInt("asset_num"));
                userInfo.setAbout(resultSet.getString("about"));
                //userInfo.setAvatarUrl(resultSet.getString("avatar_url"));
                userInfo.setCollege(resultSet.getString("college"));
                userInfo.setSno(resultSet.getString("sno"));
                userInfo.setMajor(resultSet.getString("major"));
                userInfo.setSchool(resultSet.getString("school"));
                userInfo.setName(resultSet.getString("name"));
                userInfo.setGender(resultSet.getBoolean("gender"));
                userInfo.setPhone(resultSet.getString("phone"));
                userInfo.setEmail(resultSet.getString("email"));
                userInfo.setCreateTime(resultSet.getTimestamp("create_time"));
                userInfo.setUserName(resultSet.getString("user_name"));
                userInfo.setUserID(resultSet.getInt("user_id"));
                userInfo.setAdmin(resultSet.getBoolean("is_admin"));
                userInfo.setEnable(resultSet.getBoolean("enable"));
                userInfoList.add(userInfo);
            }
            return userInfoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserInfo> findPersonalInfoBySearch(String query) {
        String sql = "select * from personal_info join user u on u.user_id = personal_info.user_id " +
                "WHERE user_name like \"%\"?\"%\" or school like \"%\"?\"%\" or " +
                "phone = ? or email = ? or sno = ? or " +
                "college like \"%\"?\"%\" or major like \"%\"?\"%\" or " +
                "native_place like \"%\"?\"%\" or name like \"%\"?\"%\" ";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            for (int i = 1; i <= 9; i++) {
                ps.setString(i, query);
            }
            ResultSet resultSet = ps.executeQuery();
            List<UserInfo> userInfoList = new ArrayList<>();
            while (resultSet.next()) {
                //userInfo.setUserID(resultSet.getInt("user_id"));
                UserInfo userInfo = new UserInfo();
                userInfo.setNativePlace(resultSet.getString("native_place"));
                userInfo.setAge(resultSet.getInt("age"));
                userInfo.setProjectNum(resultSet.getInt("project_num"));
                userInfo.setFansNum(resultSet.getInt("fans_num"));
                userInfo.setAssetNum(resultSet.getInt("asset_num"));
                userInfo.setAbout(resultSet.getString("about"));
                //userInfo.setAvatarUrl(resultSet.getString("avatar_url"));
                userInfo.setCollege(resultSet.getString("college"));
                userInfo.setSno(resultSet.getString("sno"));
                userInfo.setMajor(resultSet.getString("major"));
                userInfo.setSchool(resultSet.getString("school"));
                userInfo.setName(resultSet.getString("name"));
                userInfo.setGender(resultSet.getBoolean("gender"));
                userInfo.setPhone(resultSet.getString("phone"));
                userInfo.setEmail(resultSet.getString("email"));
                userInfo.setCreateTime(resultSet.getTimestamp("create_time"));
                userInfo.setUserName(resultSet.getString("user_name"));
                userInfo.setUserID(resultSet.getInt("user_id"));
                userInfo.setAdmin(resultSet.getBoolean("is_admin"));
                userInfo.setEnable(resultSet.getBoolean("enable"));
                userInfoList.add(userInfo);
            }
            return userInfoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
