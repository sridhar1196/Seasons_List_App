package com.example.sridh.tabbed.utilities;

import java.io.Serializable;

public class Episode_Details implements Serializable {

    String rating;
    String duration;
    String title;
    String TVDB;
    String Image_URL;
    String season;
    String episode_number;
    String overview;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTVDB() {
        return TVDB;
    }

    public void setTVDB(String TVDB) {
        this.TVDB = TVDB;
    }

    public String getImage_URL() {
        return Image_URL;
    }

    public void setImage_URL(String image_URL) {
        Image_URL = image_URL;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(String episode_number) {
        this.episode_number = episode_number;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return "Episode_Details{" +
                "rating='" + rating + '\'' +
                ", duration='" + duration + '\'' +
                ", title='" + title + '\'' +
                ", TVDB='" + TVDB + '\'' +
                ", Image_URL='" + Image_URL + '\'' +
                ", season='" + season + '\'' +
                ", episode_number='" + episode_number + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
