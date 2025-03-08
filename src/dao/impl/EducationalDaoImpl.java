package dao.impl;

import dao.EducationalDao;
import domain.Educational;
import util.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EducationalDaoImpl implements EducationalDao {
    @Override
    public boolean insertEmptyEducational(int userID) {
        String sql = "INSERT INTO `educational`(user_id) VALUES (?)";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteEducational(int id) {
        String sql = "delete from educational where id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateEducational(int id, String name, String school, String major, String degree) {
        String sql = "update educational set name = ?, school = ?, major = ?, degree = ? where id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, school);
            ps.setString(3, major);
            ps.setString(4, degree);
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Educational> findEducationalByUserID(int userID) {
        String sql = "select * from educational where user_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet resultSet = ps.executeQuery();
            List<Educational> educationalList = new ArrayList<>();
            while (resultSet.next()) {
                Educational educational = new Educational();
                educational.setId(resultSet.getInt("id"));
                educational.setSchool(resultSet.getString("school"));
                educational.setMajor(resultSet.getString("major"));
                educational.setDegree(resultSet.getString("degree"));
                educational.setName(resultSet.getString("name"));
                educationalList.add(educational);
            }
            return educationalList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
