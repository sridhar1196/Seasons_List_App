package com.example.sridh.tabbed;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sridh.tabbed.utilities.Episodes_list;
import com.example.sridh.tabbed.utilities.GetDataTVDB_Episodes;
import com.example.sridh.tabbed.utilities.Get_Data_List;
import com.example.sridh.tabbed.utilities.ShowDetails;

import static com.example.sridh.tabbed.MainActivity.URL;

public class Splash extends AppCompatActivity {
    ShowDetails showDetails = new ShowDetails();
    int length = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = this.getIntent();
        showDetails = (ShowDetails) intent.getExtras().getParcelable("ShowDetail");

        for(int i = 0;i<showDetails.getSeasonDetails().size();i++){
            Episodes_list episodesList = new Episodes_list();
            episodesList.setSeason_number(String.valueOf(i));
            Log.w("Season number",showDetails.getSeasonDetails().get(i).getSeason());
            for(int j = 0;j<Integer.valueOf(showDetails.getSeasonDetails().get(i).getEpisode_count());j++){
                new GetDataTVDB_Episodes(this,episodesList, new GetDataTVDB_Episodes.AsyncResponse() {

                    @Override
                    public void processFinish(Episodes_list episodes_list) {
                        ShowDetails showDetails = new ShowDetails();
                        showDetails = getShowDetails();
                        showDetails.getSeasonDetails().get(Integer.valueOf(episodes_list.getSeason_number())).setEpisode_details(episodes_list.getEpisode_details());
                        setShowDetails(showDetails);
                        if(showDetails.getSeasonDetails().get(showDetails.getSeasonDetails().size() - 1).getEpisode_details().size() == Integer.valueOf(showDetails.getSeasonDetails().get(showDetails.getSeasonDetails().size() - 1).getEpisode_count())){

                            if(length == 0){
                                length = length + 1;
                                new Get_Data_List(Splash.this, showDetails, new Get_Data_List.AsyncResponse() {
                                    @Override
                                    public void processFinish(ShowDetails showDetails) {
                                        Log.d("Show Detail",getShowDetails().toString());
                                        Intent intent = new Intent(Splash.this,ScrollingActivity.class);
                                        intent.putExtra("ShowDetail", (Parcelable) showDetails);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).execute(URL + showDetails.getSlug() + "/lists/personal/popular");
                            }
                        }
                    }

                }).execute(URL + showDetails.getSlug() + "/seasons/" + showDetails.getSeasonDetails().get(i).getSeason() +"/episodes/" + (j + 1) + "?extended=full");
            }
        }
    }

    public ShowDetails getShowDetails() {
        return showDetails;
    }

    public void setShowDetails(ShowDetails showDetails) {
        this.showDetails = showDetails;
    }
}
