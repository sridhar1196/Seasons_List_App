package com.example.sridh.tabbed.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;

public class SeasonUtil {
    static public class SeasonJSONParser{
        static ArrayList<ShowDetails> parseSeason(String in) throws JSONException {
            ArrayList<ShowDetails> showDetails = new ArrayList<ShowDetails>();
            JSONArray seasonsJSONArray = new JSONArray(in);
            for(int i = 0; i < seasonsJSONArray.length();i++){
                ShowDetails shows = new ShowDetails();
                JSONObject shows_details = seasonsJSONArray.getJSONObject(i);
                if(shows_details.has("show")){
                    JSONObject show = shows_details.getJSONObject("show");
                    shows.setTitle(show.getString("title"));
                    JSONObject ids = show.getJSONObject("ids");
                    shows.setSlug(ids.getString("slug"));
                    shows.setTVDB(ids.getString("tvdb"));
                    showDetails.add(shows);
                } else {
                    shows.setTitle(shows_details.getString("title"));
                    JSONObject ids = shows_details.getJSONObject("ids");
                    shows.setSlug(ids.getString("slug"));
                    shows.setTVDB(ids.getString("tvdb"));
                    showDetails.add(shows);
                }
            }
            return showDetails;
        }

        static Images_List parseSeasonImage(String in) throws JSONException{
            Images_List images_list = new Images_List();

            JSONObject root = new JSONObject(in);
            if(root.has("tvposter")){
                JSONArray logoArray = root.getJSONArray("tvposter");
                for(int i = 0; i < logoArray.length(); i++){
                    JSONObject logoobject = logoArray.getJSONObject(i);
                    String logourl = logoobject.getString("url");
                    if(logoobject.has("lang")){
                        String logoLang = logoobject.getString("lang");
                        if(logoLang.equals("en") || logoLang.equals("")){
                            images_list.setLogo_url(logourl);
                            break;
                        }
                    }
                }
            }
            if(root.has("showbackground")){
                JSONArray posterArray = root.getJSONArray("showbackground");
                for(int i = 0; i < posterArray.length(); i++){
                    JSONObject posterobject = posterArray.getJSONObject(i);
                    String posterurl = posterobject.getString("url");
                    if(posterobject.has("lang")){
                        String posterLang = posterobject.getString("lang");
                        if(posterLang.equals("en") || posterLang.equals("")){
                            images_list.setPoster_url(posterurl);
                            break;
                        }
                    }
                }
            }
            return  images_list;
        }

        static ShowDetails parseSeasonInfo(String in, ShowDetails showDetails) throws JSONException{
            JSONObject root = new JSONObject(in);
            showDetails.setOverview(root.getString("overview"));
            showDetails.setFirst_aired(root.getString("first_aired"));
            showDetails.setRuntime(root.getString("runtime"));
            showDetails.setNetwork(root.getString("network"));
            showDetails.setUpdated_at(root.getString("updated_at"));
            showDetails.setRating(root.getString("rating"));
            JSONArray genreArray =  root.getJSONArray("genres");
            ArrayList<String> gen = new ArrayList<String>();
            for(int a = 0;a < genreArray.length();a++){
                gen.add(genreArray.getString(a));
            }
            showDetails.setGenre(gen);
            showDetails.setAired_episodes(root.getString("aired_episodes"));
            return showDetails;
        }

        static String parseTVDB_Login(String in) throws JSONException {
            JSONObject root = new JSONObject(in);
            if(root.has("token")){
                return root.getString("token");
            } else {
                return null;
            }
        }

        static ShowDetails parseSeasonDetails(String in, ShowDetails showDetails) throws JSONException{
            JSONArray root = new JSONArray(in);
            for(int i = 0; i < root.length();i++){
                JSONObject season_details = root.getJSONObject(i);
                SeasonDetails seasonDetails = new SeasonDetails();
                String number = season_details.getString("number");
                if(!(number.trim().equals("0"))){
                    seasonDetails.setSeason(number);
                    seasonDetails.setRating(season_details.getString("rating"));
                    seasonDetails.setEpisode_count(season_details.getString("aired_episodes"));
                    seasonDetails.setOverview(season_details.getString("overview"));
                    seasonDetails.setFirst_aired(season_details.getString("first_aired"));
                    showDetails.getSeasonDetails().add(seasonDetails);
                }
            }
            return showDetails;
        }

        static Episodes_list parseEpisodeInfo(String in, Episodes_list showDetails) throws JSONException{
            JSONObject root = new JSONObject(in);
            Episode_Details episodesList = new Episode_Details();
            episodesList.setEpisode_number(root.getString("number"));
            episodesList.setTitle(root.getString("title"));
            episodesList.setOverview(root.getString("overview"));
            episodesList.setRating(root.getString("rating"));
            episodesList.setDuration(root.getString("runtime"));
            showDetails.getEpisode_details().add(episodesList);
            return showDetails;
        }

        static ShowDetails parseSeasonLists(String in, ShowDetails showDetails) throws JSONException{
            JSONArray root = new JSONArray(in);
            if(root != null){
                for(int i = 0; i < root.length();i++){
                    Lists lists = new Lists();
                    JSONObject season_details = root.getJSONObject(i);
                    String name = season_details.getString("name");
                    String description = season_details.getString("description");
                    if(!(name.trim().isEmpty()) && !(description.trim().isEmpty())){
                        lists.setName(name.trim());
                        lists.setDescription(description.trim());
                        showDetails.getLists().add(lists);
                    }
                }
            }
            return showDetails;
        }

    }
}
