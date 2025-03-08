package domain;

public class Project {
    private int id;
    private int userID;
    private String name;
    private String projectTime;
    private String projectDescription;
    private String stack;
    private String result;

    public Project() {
    }

    public Project(int id, int userID, String name, String projectTime, String projectDescription, String stack, String result) {
        this.id = id;
        this.userID = userID;
        this.name = name;
        this.projectTime = projectTime;
        this.projectDescription = projectDescription;
        this.stack = stack;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectTime() {
        return projectTime;
    }

    public void setProjectTime(String projectTime) {
        this.projectTime = projectTime;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
