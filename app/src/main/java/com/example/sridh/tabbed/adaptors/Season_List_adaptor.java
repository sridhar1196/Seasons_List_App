package com.example.sridh.tabbed.adaptors;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sridh.tabbed.R;
import com.example.sridh.tabbed.ScrollingActivity;
import com.example.sridh.tabbed.utilities.SeasonDetails;
import com.example.sridh.tabbed.utilities.ShowDetails;

import java.util.ArrayList;

/**
 * Created by sridh on 2/5/2018.
 */

public class Season_List_adaptor extends RecyclerView.Adapter<Season_List_adaptor.ViewHolder> {

    ScrollingActivity scrollingActivity;
    int location_list;
    ShowDetails showDetails= new ShowDetails();
    public Season_List_adaptor(ScrollingActivity scrollingActivity, int location_list, ShowDetails showDetails) {
        this.scrollingActivity = scrollingActivity;
        this.location_list = location_list;
        this.showDetails = showDetails;
    }

    @Override
    public Season_List_adaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lists, parent, false);
        Season_List_adaptor.ViewHolder viewHolder = new Season_List_adaptor.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(showDetails.getLists() != null){
            holder.name.setText(showDetails.getLists().get(position).getName().trim());
            holder.name.setText(showDetails.getLists().get(position).getDescription().trim());
        }

    }

    @Override
    public int getItemCount() {
        if(showDetails.getLists() != null){
            return  showDetails.getLists().size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, description;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.list_name);
            description = (TextView) itemView.findViewById(R.id.list_description);

        }
    }
}
