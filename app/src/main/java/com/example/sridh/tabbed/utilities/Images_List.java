package com.example.sridh.tabbed.utilities;

public class Images_List {
    String logo_url, poster_url;

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

    @Override
    public String toString() {
        return "Images_List{" +
                "logo_url='" + logo_url + '\'' +
                ", poster_url='" + poster_url + '\'' +
                '}';
    }
}
