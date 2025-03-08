package dao;

import domain.Educational;

import java.util.List;

public interface EducationalDao {

    boolean insertEmptyEducational(int userID);

    boolean deleteEducational(int id);

    boolean updateEducational(int id, String name, String school, String major, String degree);

    List<Educational> findEducationalByUserID(int userID);
}
