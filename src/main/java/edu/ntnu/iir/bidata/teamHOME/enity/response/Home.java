package edu.ntnu.iir.bidata.teamHOME.enity.response;

public class Home {
    private String name;
    private String id;

    public Home(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
