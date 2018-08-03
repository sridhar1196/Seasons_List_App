package com.example.sridh.tabbed;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sridh.tabbed.adaptors.Episode_List_Adaptor;
import com.example.sridh.tabbed.adaptors.Season_List_adaptor;
import com.example.sridh.tabbed.utilities.ShowDetails;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity{
    static ShowDetails showDetails = new ShowDetails();
    TextView show_title, show_genre, show_rating, show_seasons, show_update;
    ImageView show_poster, show_logo;
    LinearLayout llExpandable;
    RecyclerView.Adapter mAdapter;
    ShowDetails shows = new ShowDetails();

    public static ShowDetails getShowDetails() {
        return showDetails;
    }

    public void setShowDetails(ShowDetails showDetails) {
        this.showDetails = showDetails;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("showDetails",shows);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            showDetails = (ShowDetails) savedInstanceState.getParcelable("showDetails");
        }
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        show_title = findViewById(R.id.show_title);
        show_genre = findViewById(R.id.show_genre);
        show_rating = findViewById(R.id.show_rating);
        show_poster = findViewById(R.id.show_poster_image);
        show_logo = findViewById(R.id.show_icon_image);
        show_update = findViewById(R.id.show_update);
        show_seasons = findViewById(R.id.show_seasons);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = this.getIntent();
        showDetails = (ShowDetails) intent.getExtras().getParcelable("ShowDetail");
        shows =  showDetails;
        show_title.setText(showDetails.getTitle());
        try {
            String dateString = showDetails.getUpdated_at();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = sdf.parse(dateString);

            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM yyyy");
            String formattedDate = outputFormat.format(date);
            show_update.setText(formattedDate  + " - ");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        show_seasons.setText(showDetails.getSeasonDetails().size() + " Seasons");

        Picasso.with(ScrollingActivity.this)
                .load(showDetails.getPoster_url())
                .into(show_poster);
        Picasso.with(ScrollingActivity.this)
                .load(showDetails.getLogo_url())
                .into(show_logo);
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        show_rating.setText(String.valueOf((float)Math.round(Float.valueOf(showDetails.getRating()) * 100) / 100));
        StringBuilder genre = new StringBuilder();
        for(int i = 0; i < showDetails.getGenre().size();i++){
            if(i != 0){
                genre.append(" | ");
            }

            genre.append(showDetails.getGenre().get(i));
        }
        show_genre.setText(genre);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);
//
//        new GetDataTVDB_Episodes(ScrollingActivity.this,showDetails, new GetDataTVDB_Episodes.AsyncResponse() {
//
//            @Override
//            public void processFinish(ShowDetails showDetails) {
//                Log.d("Show Details",showDetails.toString());
//                setShowDetails(showDetails);
//            }
//
//        }).execute(URL_TVDB + showDetails.getTVDB() + "/episodes");
//


        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OverviewFragment(), "Overview");
        adapter.addFrag(new EpisodeListFragment(),"Episodes");
        adapter.addFrag(new SeasonListFragment(), "Lists");
        viewPager.setAdapter(adapter);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static class OverviewFragment extends android.support.v4.app.Fragment {
        //Episode_List_Adaptor adapter;

        public OverviewFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.overview_fragment, container, false);
            TextView overview = view.findViewById(R.id.overview);
            overview.setText(ScrollingActivity.getShowDetails().getOverview());

            return view;
        }
    }

    @SuppressLint("ValidFragment")
    public static class SeasonListFragment extends android.support.v4.app.Fragment{

        Episode_List_Adaptor mCrimeExpandableAdapter;
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        LinearLayoutManager mLayoutManager;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.season_list_fragment,container,false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.lists);

            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager((ScrollingActivity) getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration((ScrollingActivity) getActivity(), mLayoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            mAdapter = new Season_List_adaptor((ScrollingActivity) getActivity(), R.layout.lists, ScrollingActivity.getShowDetails());
            mRecyclerView.setAdapter(mAdapter);
            return view;
        }
    }

    @SuppressLint("ValidFragment")
    public static class EpisodeListFragment extends android.support.v4.app.Fragment{

        Episode_List_Adaptor mCrimeExpandableAdapter;
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        LinearLayoutManager mLayoutManager;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.episode_list_fragment,container,false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.lvExp);

            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager((ScrollingActivity) getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration((ScrollingActivity) getActivity(), mLayoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);
            mAdapter = new Episode_List_Adaptor((ScrollingActivity) getActivity(), R.layout.episode_list_fragment, ScrollingActivity.getShowDetails());
            mRecyclerView.setAdapter(mAdapter);
            return view;
        }
    }

}

