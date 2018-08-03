package com.example.sridh.tabbed.utilities;

import java.util.ArrayList;

public class Episodes_list {
    String season_number;
    ArrayList<Episode_Details> episode_details = new ArrayList<Episode_Details>();

    public String getSeason_number() {
        return season_number;
    }

    public void setSeason_number(String season_number) {
        this.season_number = season_number;
    }

    public ArrayList<Episode_Details> getEpisode_details() {
        return episode_details;
    }

    public void setEpisode_details(ArrayList<Episode_Details> episode_details) {
        this.episode_details = episode_details;
    }

    @Override
    public String toString() {
        return "Episodes_list{" +
                "season_number='" + season_number + '\'' +
                ", episode_details=" + episode_details +
                '}';
    }
}
