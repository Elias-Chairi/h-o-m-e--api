package edu.ntnu.iir.bidata.teamHOME.enity.GetTasksReposponse;

import java.util.List;

/**
 * Response object for getting tasks.
 */
public class GetTasksReposponseNEW {

    private Home home;
    
    public class Home {
        private String name;
        private String id;
        private List<User> users;
        private List<Task> tasks;
        
        public Home(String name, String id, List<User> users, List<Task> tasks) {
            this.name = name;
            this.id = id;
            this.users = users;
            this.tasks = tasks;
        }
        
        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
        
        public List<User> getUsers() {
            return users;
        }
        
        public List<Task> getTasks() {
            return tasks;
        }        
    }

    public GetTasksReposponseNEW(String homeName, String homeID, List<User> users, List<Task> tasks) {
        this.home = new Home(homeName, homeID, users, tasks);
    }

    public Home getHome() {
        return home;
    }
}
