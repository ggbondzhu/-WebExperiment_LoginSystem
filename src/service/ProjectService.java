package service;

import domain.Project;

import java.util.List;

public interface ProjectService {
    boolean insertEmptyProject(int userID);

    boolean deleteProject(int id);

    boolean updateProject(int id, String name, String projectTime, String projectDescription, String stack, String result);

    List<Project> findProjectByUserID(int userID);
}
