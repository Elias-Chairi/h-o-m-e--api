package edu.ntnu.iir.bidata.teamHOME.enity.GetTasksReposponse;

import java.util.List;

/**
 * Response object for getting tasks.
 */
public class GetTasksReposponse {
    private String homeName;
    private List<User> users;
    private List<Task> tasks;

    public GetTasksReposponse(String homeName, List<User> users, List<Task> tasks) {
        this.homeName = homeName;
        this.users = users;
        this.tasks = tasks;
    }

    public String getHomeName() {
        return homeName;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
