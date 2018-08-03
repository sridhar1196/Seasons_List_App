package com.example.sridh.tabbed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sridh.tabbed.adaptors.SeasonListAdaptor;
import com.example.sridh.tabbed.utilities.Get_Data_Image;
import com.example.sridh.tabbed.utilities.Get_Data_TVDB_Login;
import com.example.sridh.tabbed.utilities.Get_Data_Trakt;
import com.example.sridh.tabbed.utilities.Images_List;
import com.example.sridh.tabbed.utilities.ShowDetails;

import java.net.InetAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static String TRAKT_KEY = "9b240d5869b18d496c9876019289b811cb49a1ede7bb2029f9a4202042b01f7e";
    public final static String FANART_KEY = "23bc4683c6457409a42caa90b30252b8";
    public final static String TVDB_KEY = "ECE04396F2545DE2";
    public static String TVDB_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTY1MzY0NDUsImlkIjoiVHJha3RBcHAiLCJvcmlnX2lhdCI6MTUxNjQ1MDA0NX0.Hwgau92EGossCqg1BlKkPuWaMDahWqQjUQqCiUePrcE2kv83a0QeDuXEgtYQJxQO7r_luiP1H40x1n5NAXmzhGeArlvnRj394JFK1PjIxKJi2Dm_pIeBW-RyZOjTCida4Qq7IinTJISMUm43k6hdtk_qeCb6q0CE6a-UW-PNCpSgvfDWVBEzztDa1JbSi33NhgQqNfxOwYbtDm53GUfsCyaJNre_kwVbvhHUvPCGDcSBOA_tWXXj-LpwWhkT1EKSt5H4dORO84diQ1LCfTQjxBKndcMAXk3YsaXrwhZK7X4Crdul08z2muVPFvQVz2V79PU4obWsC6q9aq5JwVnpbA";
    public final static String URL = "https://api.trakt.tv/shows/";
    public final static String URL_FANART = "http://webservice.fanart.tv/v3/tv/";
    public final static String URL_TVDB = "https://api.thetvdb.com/series/";
    public final static String URL_TVDB_LOGIN = "https://api.thetvdb.com/login";
    public final static int DETAILS_SCREEN = 100;

    ArrayList<ShowDetails> shows = new ArrayList<ShowDetails>();

    int length = 0;

    RecyclerView.Adapter mAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    public static String getTvdbToken() {
        return TVDB_TOKEN;
    }

    public static void setTvdbToken(String tvdbToken) {
        TVDB_TOKEN = tvdbToken;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            shows = (ArrayList<ShowDetails>) savedInstanceState.getSerializable("LIST");
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null){
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.trending_shows);

//        new Get_Data_TVDB_Login(MainActivity.this, new Get_Data_TVDB_Login.AsyncResponse() {
//
//            @Override
//            public void processFinish(String token) {
//                Log.d("token",token);
//                setTvdbToken(token);
//            }
//
//        }).execute(URL_TVDB_LOGIN + "?apikey="+TVDB_KEY);

        StringBuilder url = new StringBuilder(URL);
        mRecyclerView = (RecyclerView) findViewById(R.id.season_list);
        if(isConnected() ){
            Get_data(url.append("trending").toString());
        } else {
            Toast.makeText(this,"No Internet connection",Toast.LENGTH_SHORT).show();
        }
        countTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("LIST",shows);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null){
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        StringBuilder url = new StringBuilder(URL);
        mRecyclerView = (RecyclerView) findViewById(R.id.season_list);

        if(isConnected() ){
            if (id == R.id.trending_shows) {
                url.append("trending");
            } else if (id == R.id.popular_shows) {
                url.append("popular");
            } else if (id == R.id.most_watched_shows) {
                url.append("watched/period");
            } else if (id == R.id.most_played_shows) {
                url.append("played/period");
            } else if (id == R.id.most_collected_shows) {
                url.append("collected/period");
            }
            Get_data(url.toString());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null){
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    public void countTimer(){
        new CountDownTimer(86400000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                new Get_Data_TVDB_Login(MainActivity.this, new Get_Data_TVDB_Login.AsyncResponse() {

                    @Override
                    public void processFinish(String token) {
                        Log.d("token",token);
                        setTvdbToken(token);
                    }

                }).execute(URL_TVDB_LOGIN + "?apikey="+TVDB_KEY);
                countTimer();
            }
        }.start();
    }

    public boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<ShowDetails> getShows() {
        return shows;
    }

    public void setShows(ArrayList<ShowDetails> shows) {
        this.shows = shows;
    }

    public void Get_data(String url){
        new Get_Data_Trakt(MainActivity.this, new Get_Data_Trakt.AsyncResponse() {

            @Override
            public void processFinish(final ArrayList<ShowDetails> showDetails) {
                if(showDetails != null){
                    shows =  showDetails;
                    length = 0;
                    for(int i = 0;i<showDetails.size();i++){
                        Log.d("Seasons",shows.get(i).toString());
                        new Get_Data_Image(MainActivity.this, new Get_Data_Image.AsyncResponse() {

                            @Override
                            public void processFinish(Images_List images_list) {
                                Log.d("Length",""+length);
                                if(images_list != null) {
                                    shows.get(length).setPoster_url(images_list.getPoster_url());
                                    shows.get(length).setLogo_url(images_list.getLogo_url());
                                }else {
                                    Toast.makeText(MainActivity.this,"Error in loading data",Toast.LENGTH_SHORT).show();
                                }
                                Log.d("Seasons",shows.toString());
                                length = length + 1;
                                if(length == (shows.size() - 1)){
                                    mRecyclerView.setHasFixedSize(true);
                                    // use a linear layout managerq
                                    mLayoutManager = new GridLayoutManager(MainActivity.this,2);
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                    //mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                    mAdapter = new SeasonListAdaptor(MainActivity.this, R.layout.card_view, shows);
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        }).execute(URL_FANART + shows.get(i).getTVDB() + "?api_key=" + FANART_KEY);
                    }
                } else {
                    Toast.makeText(MainActivity.this,"Error in loading data",Toast.LENGTH_SHORT).show();
                }
            }
        }).execute(url.toString());
    }

}
