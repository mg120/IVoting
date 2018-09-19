package com.alsalil.web.vote.AllRates;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alsalil.web.vote.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ma7MouD on 3/22/2018.
 */

public class AllRatesAdapter extends RecyclerView.Adapter<AllRatesAdapter.ViewHolder>{

    Context context;
    ArrayList<AllRatesModel> rates_list ;
    ArrayList<Integer> votes_count_list ;
    ArrayList<Integer> N_votes ;

    int x = 0 ;

    private String [] prog_colors = {"#1067AA", "#F1B310", "#01A793", "#622467", "#B2045B", "#ED6B20" , "#7AAC4B", "#9B26AF", "#68EFAD", "#FF7043", "#795548", "#C6FF00", "#FF4081", "#E040FB", "#f9254c"};

    public AllRatesAdapter(Context context, ArrayList<AllRatesModel> rates_list, ArrayList<Integer> votes_count_list, ArrayList<Integer> n_votes) {
        this.context = context;
        this.rates_list = rates_list;
        this.votes_count_list = votes_count_list;
        N_votes = n_votes;
    }

    @Override
    public AllRatesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.all_rated_recyc_item, parent , false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(AllRatesAdapter.ViewHolder holder, int position) {

        x++ ;
        if (x == prog_colors.length) {
            x = 0;
            holder.all_rated_item_prog.setMax(100);
            holder.all_rated_item_prog.setProgressTintList(ColorStateList.valueOf(Color.parseColor(prog_colors[x])));
            holder.all_rated_item_title.setText(rates_list.get(position).getItemName());
            holder.all_rated_count_num.setText(String.valueOf(votes_count_list.get(position)));
            Picasso.with(context).load("http://vote4favorite.com/vote/public/images/" + rates_list.get(position).getImageName()).into(holder.all_rated_item_img);
            holder.all_rated_item_prog.setProgress(N_votes.get(position));

        } else {
            holder.all_rated_item_prog.setMax(100);
            holder.all_rated_item_prog.setProgressTintList(ColorStateList.valueOf(Color.parseColor(prog_colors[x])));
            holder.all_rated_item_title.setText(rates_list.get(position).getItemName());
            holder.all_rated_count_num.setText(String.valueOf(votes_count_list.get(position)));
            Picasso.with(context).load("http://vote4favorite.com/vote/public/images/" + rates_list.get(position).getImageName()).into(holder.all_rated_item_img);
            holder.all_rated_item_prog.setProgress(N_votes.get(position));
        }
    }
    @Override
    public int getItemCount() {
        return rates_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView all_rated_item_title ;
        TextView all_rated_count_num ;
        ImageView all_rated_item_img ;
        ProgressBar all_rated_item_prog ;

        public ViewHolder(View itemView) {
            super(itemView);

            all_rated_item_title = (TextView) itemView.findViewById(R.id.all_rated_item_title);
            all_rated_count_num = (TextView) itemView.findViewById(R.id.all_rated_votes_number);
            all_rated_item_img = (ImageView) itemView.findViewById(R.id.all_rated_item_img);
            all_rated_item_prog = (ProgressBar) itemView.findViewById(R.id.all_rates_progress);
        }
    }
}
