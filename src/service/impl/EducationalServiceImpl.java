package service.impl;

import dao.EducationalDao;
import dao.impl.EducationalDaoImpl;
import domain.Educational;
import service.EducationalService;

import java.util.List;

public class EducationalServiceImpl implements EducationalService {
    EducationalDao educationalDao = new EducationalDaoImpl();

    @Override
    public boolean insertEmptyEducational(int userID) {
        return educationalDao.insertEmptyEducational(userID);
    }

    @Override
    public boolean deleteEducational(int id) {
        return educationalDao.deleteEducational(id);
    }

    @Override
    public boolean updateEducational(int id, String name, String school, String major, String degree) {
        return educationalDao.updateEducational(id, name, school, major, degree);
    }

    @Override
    public List<Educational> findEducationalByUserID(int userID) {
        return educationalDao.findEducationalByUserID(userID);
    }
}
