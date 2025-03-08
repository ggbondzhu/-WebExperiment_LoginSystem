package service;

import domain.Educational;

import java.util.List;

public interface EducationalService {
    boolean insertEmptyEducational(int userID);

    boolean deleteEducational(int id);

    boolean updateEducational(int id, String name, String school, String major, String degree);

    List<Educational> findEducationalByUserID(int userID);
}
