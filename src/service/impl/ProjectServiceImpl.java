package service.impl;

import dao.ProjectDao;
import dao.impl.ProjectDaoImpl;
import domain.Project;
import service.ProjectService;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {

    ProjectDao projectDao = new ProjectDaoImpl();

    @Override
    public boolean insertEmptyProject(int userID) {
        return projectDao.insertEmptyProject(userID);
    }

    @Override
    public boolean deleteProject(int id) {
        return projectDao.deleteProject(id);
    }

    @Override
    public boolean updateProject(int id, String name, String projectTime, String projectDescription, String stack, String result) {
        return projectDao.updateProject(id, name, projectTime, projectDescription, stack, result);
    }

    @Override
    public List<Project> findProjectByUserID(int userID) {
        return projectDao.findProjectByUserID(userID);
    }
}
