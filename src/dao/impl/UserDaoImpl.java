package dao.impl;

import dao.UserDao;
import domain.User;
import domain.UserCheck;
import domain.UserInfo;
import util.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public UserCheck findUserByNamePwd(String username, String password) {
        String sql = "select * from user where user_name = ? and password = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                UserCheck userCheck = new UserCheck();
                userCheck.setUserName(resultSet.getString("user_name"));
                userCheck.setAdmin(resultSet.getBoolean("is_admin"));
                userCheck.setEnable(resultSet.getBoolean("enable"));
                return userCheck;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public UserCheck findUserByEmailPwd(String email, String password) {
        String sql = "select * from user where email = ? and password = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                UserCheck userCheck = new UserCheck();
                userCheck.setUserName(resultSet.getString("user_name"));
                userCheck.setAdmin(resultSet.getBoolean("is_admin"));
                userCheck.setEnable(resultSet.getBoolean("enable"));
                return userCheck;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public UserCheck findUserByPhonePwd(String phone, String password) {
        String sql = "select * from user where phone = ? and password = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, phone);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                UserCheck userCheck = new UserCheck();
                userCheck.setUserName(resultSet.getString("user_name"));
                userCheck.setAdmin(resultSet.getBoolean("is_admin"));
                userCheck.setEnable(resultSet.getBoolean("enable"));
                return userCheck;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insertNewUser(String username, String password) {
        String sql = "INSERT INTO `user`(user_name, `password`) VALUES(?,?)";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            return false;//throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public UserCheck findUserByNameEmailPhone(String username, String email, String phone) {
        String sql = "select * from user where user_name = ? and email = ? and phone = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, phone);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                UserCheck userCheck = new UserCheck();
                userCheck.setUserName(resultSet.getString("user_name"));
                userCheck.setAdmin(resultSet.getBoolean("is_admin"));
                userCheck.setEnable(resultSet.getBoolean("enable"));
                return userCheck;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public UserCheck findUserByEmail(String email) {
        String sql = "select * from user where email = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                UserCheck userCheck = new UserCheck();
                userCheck.setUserName(resultSet.getString("user_name"));
                userCheck.setAdmin(resultSet.getBoolean("is_admin"));
                userCheck.setEnable(resultSet.getBoolean("enable"));
                return userCheck;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<User> findAllUser() {
        String sql = "select * from user order by user_id";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setUserName(resultSet.getString("user_name"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
                user.setEnable(resultSet.getBoolean("enable"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setUserID(resultSet.getInt("user_id"));
                user.setCreateTime(resultSet.getTimestamp("create_time"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean changePassword(String username, String password) {
        String sql = "UPDATE `user` SET `password`=? WHERE user_name=?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, username);
            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM `user` WHERE user_name=?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, username);
            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean changeUserState(String username) {
        String sql = "select enable from user where user_name = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                boolean enable = resultSet.getBoolean("enable");
                String sql2 = "UPDATE `user` SET `enable`=? WHERE user_name=?";
                ps = JdbcUtils.conn.prepareStatement(sql2);
                ps.setBoolean(1, !enable);
                ps.setString(2, username);
                int result = ps.executeUpdate();
                if (result == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUserPhoneEmail(UserInfo userInfo, String username) {
        String sql = "UPDATE `user` SET `email` = IFNULL(?,`user`.email), " +
                " `phone` = IFNULL(?,`user`.phone) WHERE `user_name` = ?;";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, userInfo.getEmail());
            ps.setString(2, userInfo.getPhone());
            ps.setString(3, username);
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
}
