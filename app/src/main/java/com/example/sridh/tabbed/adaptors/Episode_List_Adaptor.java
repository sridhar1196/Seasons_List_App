package com.example.sridh.tabbed.adaptors;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridh.tabbed.MainActivity;
import com.example.sridh.tabbed.R;
import com.example.sridh.tabbed.ScrollingActivity;
import com.example.sridh.tabbed.utilities.SeasonDetails;
import com.example.sridh.tabbed.utilities.ShowDetails;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Episode_List_Adaptor extends RecyclerView.Adapter<Episode_List_Adaptor.ViewHolder> {

    ScrollingActivity scrollingActivity;
    int location_list;
    ArrayList<SeasonDetails> seasonDetails = new ArrayList<SeasonDetails>();
    public Episode_List_Adaptor(ScrollingActivity scrollingActivity, int location_list, ShowDetails seasonDetails) {
        this.scrollingActivity = scrollingActivity;
        this.location_list = location_list;
        this.seasonDetails = seasonDetails.getSeasonDetails();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_episode_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.seasonNumber.setText("Season "+seasonDetails.get(position).getSeason());
        holder.episodeCount.setText("Episode "+seasonDetails.get(position).getEpisode_count());
        holder.position = position;
        holder.scrollingActivity = scrollingActivity;

        for(int i = 0;i<seasonDetails.get(position).getEpisode_details().size();i++){
            LinearLayout linearlayout = new LinearLayout(scrollingActivity);
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.setMargins(10, 15, 10, 10);
            linearlayout.setLayoutParams(buttonLayoutParams);
            linearlayout.setOrientation(LinearLayout.HORIZONTAL);
            linearlayout.setBackground(scrollingActivity.getResources().getDrawable(R.drawable.customborder));
            holder.ll_expandable.addView(linearlayout);

            ImageView imageView = new ImageView(scrollingActivity);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(300,250));
            imageView.setImageResource(R.drawable.no_images);
            linearlayout.addView(imageView);

            LinearLayout linear_Details = new LinearLayout(scrollingActivity);
            linear_Details.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linear_Details.setOrientation(LinearLayout.VERTICAL);
            linearlayout.addView(linear_Details);

            TextView episodeTitle = new TextView(scrollingActivity);
            episodeTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            episodeTitle.setText(seasonDetails.get(position).getEpisode_details().get(i).getTitle());
            episodeTitle.setTypeface(episodeTitle.getTypeface(), Typeface.BOLD);
            linear_Details.addView(episodeTitle);

            TextView episodeDuration = new TextView(scrollingActivity);
            episodeDuration.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int hours = Integer.valueOf(seasonDetails.get(position).getEpisode_details().get(i).getDuration()) / 60;
            int minutes = Integer.valueOf(seasonDetails.get(position).getEpisode_details().get(i).getDuration()) % 60;
            System.out.printf("%d:%02d", hours, minutes);
            episodeDuration.setText(hours+":"+minutes+":00");
            linear_Details.addView(episodeDuration);

            LinearLayout linear_Rating = new LinearLayout(scrollingActivity);
            linear_Rating.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linear_Rating.setOrientation(LinearLayout.HORIZONTAL);
            linear_Details.addView(linear_Rating);


            TextView episodeRating = new TextView(scrollingActivity);
            episodeRating.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            episodeRating.setText(String.valueOf((float)Math.round(Float.valueOf(seasonDetails.get(position).getEpisode_details().get(i).getRating()) * 100) / 100));
            episodeRating.setTypeface(episodeRating.getTypeface(),Typeface.BOLD);
            episodeRating.setTextColor(scrollingActivity.getResources().getColor(R.color.green));
            linear_Rating.addView(episodeRating);

            TextView episodeRatingMax = new TextView(scrollingActivity);
            episodeRatingMax.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            episodeRatingMax.setText("/10");
            linear_Rating.addView(episodeRatingMax);
        }
    }

    @Override
    public int getItemCount() {
        if(seasonDetails != null){
            return  seasonDetails.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView seasonNumber, episodeCount;
        ImageView item_arrow;
        LinearLayout ll_expandable;
        ScrollingActivity scrollingActivity;
        int position;
        public ViewHolder(View itemView) {
            super(itemView);
            seasonNumber = (TextView) itemView.findViewById(R.id.seasonNumber);
            episodeCount = (TextView) itemView.findViewById(R.id.episodeCount);
            item_arrow = (ImageView) itemView.findViewById(R.id.item_arrow);
            ll_expandable = (LinearLayout) itemView.findViewById(R.id.ll_expandable);
            item_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ll_expandable.getVisibility() == View.GONE){
                        item_arrow.setImageResource(R.drawable.icons8_collapse_arrow_24);
                        ll_expandable.setVisibility(View.VISIBLE);
                    } else {
                        item_arrow.setImageResource(R.drawable.icons8_expand_arrow_24);
                        ll_expandable.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}