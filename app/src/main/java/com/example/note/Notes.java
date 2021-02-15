package com.example.note;

import java.io.Serializable;

public class Notes implements Serializable {
    private String Title;

    private String Description;

    private int ID;

    public Notes(){}

    public Notes(String title, String description, int ID) {
        Title = title;
        Description = description;
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
