package dao.impl;

import dao.ProjectDao;
import domain.Project;
import util.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {
    @Override
    public boolean insertEmptyProject(int userID) {
        String sql = "INSERT INTO `project`(user_id) VALUES (?)";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            return false;
        }
    }

    @Override
    public boolean deleteProject(int id) {
        String sql = "delete from project where id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateProject(int id, String name, String projectTime, String projectDescription, String stack, String result) {
        String sql = "update project set name = ?, project_time = ?, project_description = ?, stack = ?, result = ? where id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, projectTime);
            ps.setString(3, projectDescription);
            ps.setString(4, stack);
            ps.setString(5, result);
            ps.setInt(6, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Project> findProjectByUserID(int userID) {
        String sql = "select * from project where user_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet resultSet = ps.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setProjectTime(resultSet.getString("project_time"));
                project.setProjectDescription(resultSet.getString("project_description"));
                project.setStack(resultSet.getString("stack"));
                project.setResult(resultSet.getString("result"));
                projects.add(project);
            }
            return projects;
        } catch (SQLException e) {
            return null;
        }
    }
}
