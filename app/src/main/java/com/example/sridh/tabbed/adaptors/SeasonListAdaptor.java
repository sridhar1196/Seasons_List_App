package com.example.sridh.tabbed.adaptors;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridh.tabbed.MainActivity;
import com.example.sridh.tabbed.R;
import com.example.sridh.tabbed.ScrollingActivity;
import com.example.sridh.tabbed.Splash;
import com.example.sridh.tabbed.utilities.Episodes_list;
import com.example.sridh.tabbed.utilities.GetDataTVDB_Episodes;
import com.example.sridh.tabbed.utilities.Get_Data_Season_Details;
import com.example.sridh.tabbed.utilities.ShowDetails;
import com.example.sridh.tabbed.utilities.Get_Data_Show_Summary;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.sridh.tabbed.MainActivity.URL;
import static com.example.sridh.tabbed.MainActivity.URL_TVDB;

public class SeasonListAdaptor extends RecyclerView.Adapter<SeasonListAdaptor.ViewHolder>  {
    MainActivity mainActivity;
    int location_list;
    int length = 0;
    int season_number = 0;
    ArrayList<ShowDetails> showDetails = new ArrayList<ShowDetails>();
    ShowDetails single_showDetails = new ShowDetails();

    public ShowDetails getSingle_showDetails() {
        return single_showDetails;
    }

    public void setSingle_showDetails(ShowDetails single_showDetails) {
        this.single_showDetails = single_showDetails;
    }

    public SeasonListAdaptor(MainActivity mainActivity, int location_list, ArrayList<ShowDetails> showDetails) {
        this.mainActivity = mainActivity;
        this.location_list = location_list;
        this.showDetails = showDetails;

    }

    @Override
    public SeasonListAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final SeasonListAdaptor.ViewHolder holder, int position) {
        final int index = position;
        holder.context = mainActivity;
        holder.position = position;
        holder.showDetails = showDetails;
        holder.show_title.setText(showDetails.get(position).getTitle().trim());
        if((showDetails.get(position).getLogo_url() == null)||(showDetails.get(position).getPoster_url() == null)){
            holder.poster.setImageResource(R.drawable.no_images);
        } else {
            Picasso.with(mainActivity)
                    .load(showDetails.get(position).getLogo_url())
                    .into(holder.poster);
        }

    }
    @Override
    public int getItemCount() {
        if(showDetails != null){
            return  showDetails.size();
        } else {
            return 0;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView show_title;
        Context context;
        int position;
        ArrayList<ShowDetails> showDetails;
        public ViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            show_title = (TextView) itemView.findViewById(R.id.show_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetails.get(position).getSeasonDetails().clear();
                    new Get_Data_Show_Summary(context,showDetails.get(position), new Get_Data_Show_Summary.AsyncResponse() {
                        @Override
                        public void processFinish(ShowDetails showDetails) {
                            new Get_Data_Season_Details(context,showDetails, new Get_Data_Season_Details.AsyncResponse() {
                                @Override
                                public void processFinish(ShowDetails showDetails) {
                                    Intent intent = new Intent(context,Splash.class);
                                    intent.putExtra("ShowDetail", (Parcelable) showDetails);
                                    context.startActivity(intent);
                                }
                            }).execute(URL + showDetails.getSlug() + "/seasons?extended=full");
                        }
                    }).execute(URL + showDetails.get(position).getSlug() + "?extended=full");

                }
            });
        }
    }

}
