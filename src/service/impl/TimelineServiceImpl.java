package service.impl;

import dao.TimelineDao;
import dao.impl.TimelineDaoImpl;
import domain.Timeline;
import service.TimelineService;

import java.util.List;

public class TimelineServiceImpl implements TimelineService {
    TimelineDao TimelineDao = new TimelineDaoImpl();

    @Override
    public boolean insertTimeline(int userID, String content, String time) {
        return TimelineDao.insertTimeline(userID, content, time);
    }

    @Override
    public boolean insertEmptyTimeline(int userID) {
        return TimelineDao.insertEmptyTimeline(userID);
    }

    @Override
    public boolean deleteTimeline(int id) {
        return TimelineDao.deleteTimeline(id);
    }

    @Override
    public boolean updateTimeline(int id, String content, String time) {
        return TimelineDao.updateTimeline(id, content, time);
    }

    @Override
    public List<Timeline> findTimelineByUserID(int userID) {
        return TimelineDao.findTimelineByUserID(userID);
    }
}
