package edu.ntnu.iir.bidata.teamHOME.enity.response;

import java.util.List;

public class HomeDetails extends Home {
    private List<User> users;
    private List<Task> tasks;

    public HomeDetails(String name, String id, List<User> users, List<Task> tasks) {
        super(name, id);
        this.users = users;
        this.tasks = tasks;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
