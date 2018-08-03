package com.example.sridh.tabbed.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeasonDetails implements Serializable{

    String season;
    ArrayList<Episode_Details> episode_details = new ArrayList<Episode_Details>();
    String seasonID;
    String rating;
    String episode_count;
    String overview;
    String first_aired;

    @Override
    public String toString() {
        return "SeasonDetails{" +
                "season='" + season + '\'' +
                ", episode_details=" + episode_details +
                ", seasonID='" + seasonID + '\'' +
                ", rating='" + rating + '\'' +
                ", episode_count='" + episode_count + '\'' +
                ", overview='" + overview + '\'' +
                ", first_aired='" + first_aired + '\'' +
                '}';
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(String episode_count) {
        this.episode_count = episode_count;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirst_aired() {
        return first_aired;
    }

    public void setFirst_aired(String first_aired) {
        this.first_aired = first_aired;
    }

    public String getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(String seasonID) {
        this.seasonID = seasonID;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public ArrayList<Episode_Details> getEpisode_details() {
        return episode_details;
    }

    public void setEpisode_details(ArrayList<Episode_Details> episode_details) {
        this.episode_details = episode_details;
    }

}
