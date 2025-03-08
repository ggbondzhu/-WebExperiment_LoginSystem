package dao;

import domain.Timeline;

import java.util.List;

public interface TimelineDao {
    boolean insertTimeline(int userID, String content, String time);

    boolean insertEmptyTimeline(int userID);

    boolean deleteTimeline(int id);

    boolean updateTimeline(int id, String content, String time);

    List<Timeline> findTimelineByUserID(int userID);

}
