package dao.impl;

import domain.Timeline;
import util.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TimelineDaoImpl implements dao.TimelineDao {

    @Override
    public boolean insertTimeline(int userID, String content, String time) {
        String sql = "INSERT INTO `timeline`(user_id, content, time) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setString(2, content);
            ps.setString(3, time);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean insertEmptyTimeline(int userID) {
        String sql = "INSERT INTO `timeline`(user_id) VALUES (?)";
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
    public boolean deleteTimeline(int id) {
        String sql = "delete from timeline where id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateTimeline(int id, String content, String time) {
        String sql = "update timeline set content = ?, time = ? where id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, content);
            ps.setString(2, time);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Timeline> findTimelineByUserID(int userID) {
        String sql = "select * from timeline where user_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet resultSet = ps.executeQuery();
            List<Timeline> timelines = new java.util.ArrayList<>();
            while (resultSet.next()) {
                Timeline timeline = new Timeline();
                timeline.setId(resultSet.getInt("id"));
                timeline.setUserID(resultSet.getInt("user_id"));
                timeline.setContent(resultSet.getString("content"));
                timeline.setTime(resultSet.getString("time"));
                timelines.add(timeline);
            }
            return timelines;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
