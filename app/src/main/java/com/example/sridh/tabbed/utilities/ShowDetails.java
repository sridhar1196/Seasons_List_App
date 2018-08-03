package com.example.sridh.tabbed.utilities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.sridh.tabbed.utilities.Episode_Details;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowDetails implements Parcelable{
    String title;
    String slug;
    String TVDB;
    String logo_url, poster_url;
    String network;
    ArrayList<String> genre = new ArrayList<String>();
    String overview;
    String rating;
    String series_id;
    String first_aired;
    String updated_at;
    String runtime;
    String aired_episodes;
    ArrayList<Lists> lists = new ArrayList<Lists>();
    ArrayList<SeasonDetails> seasonDetails = new ArrayList<SeasonDetails>();

    public String getAired_episodes() {
        return aired_episodes;
    }

    public void setAired_episodes(String aired_episodes) {
        this.aired_episodes = aired_episodes;
    }

    @Override
    public String toString() {
        return "ShowDetails{" +
                "title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", TVDB='" + TVDB + '\'' +
                ", logo_url='" + logo_url + '\'' +
                ", poster_url='" + poster_url + '\'' +
                ", network='" + network + '\'' +
                ", genre=" + genre +
                ", overview='" + overview + '\'' +
                ", rating='" + rating + '\'' +
                ", series_id='" + series_id + '\'' +
                ", first_aired='" + first_aired + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", runtime='" + runtime + '\'' +
                ", aired_episodes='" + aired_episodes + '\'' +
                ", lists=" + lists +
                ", seasonDetails=" + seasonDetails +
                '}';
    }

    public ArrayList<Lists> getLists() {
        return lists;
    }

    public void setLists(ArrayList<Lists> lists) {
        this.lists = lists;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getFirst_aired() {
        return first_aired;
    }

    public void setFirst_aired(String first_aired) {
        this.first_aired = first_aired;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public ShowDetails(Parcel source) {
        this.title = source.readString();
        this.slug = source.readString();
        this.TVDB = source.readString();
        this.logo_url = source.readString();
        this.poster_url = source.readString();
        this.network = source.readString();
        this.genre = source.readArrayList(String.class.getClassLoader());
        this.overview = source.readString();
        this.rating = source.readString();
        this.series_id = source.readString();
        this.first_aired = source.readString();
        this.updated_at = source.readString();
        this.runtime = source.readString();
        this.aired_episodes = source.readString();
        this.lists = (ArrayList<Lists>) source.readSerializable();
        this.seasonDetails = (ArrayList<SeasonDetails>) source.readSerializable();
    }

    public ShowDetails() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTVDB() {
        return TVDB;
    }

    public void setTVDB(String TVDB) {
        this.TVDB = TVDB;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSeries_id() {
        return series_id;
    }

    public void setSeries_id(String series_id) {
        this.series_id = series_id;
    }

    public ArrayList<SeasonDetails> getSeasonDetails() {
        return seasonDetails;
    }

    public void setSeasonDetails(ArrayList<SeasonDetails> seasonDetails) {
        this.seasonDetails = seasonDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(slug);
        dest.writeString(TVDB);
        dest.writeString(logo_url);
        dest.writeString(poster_url);
        dest.writeString(network);
        dest.writeList(genre);
        dest.writeString(overview);
        dest.writeString(rating);
        dest.writeString(series_id);
        dest.writeString(first_aired);
        dest.writeString(updated_at);
        dest.writeString(runtime);
        dest.writeString(aired_episodes);
        dest.writeSerializable(lists);
        dest.writeSerializable(seasonDetails);
    }

    public static final Parcelable.Creator<ShowDetails> CREATOR = new Parcelable.Creator<ShowDetails>(){

        @Override
        public ShowDetails createFromParcel(Parcel source) {
            return new ShowDetails(source);
        }

        @Override
        public ShowDetails[] newArray(int size) {
            return new ShowDetails[size];
        }
    };
}
