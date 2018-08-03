package com.example.sridh.tabbed.utilities;

import java.io.Serializable;

/**
 * Created by sridh on 2/4/2018.
 */

public class Lists implements Serializable{
    String name;
    String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Lists{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
