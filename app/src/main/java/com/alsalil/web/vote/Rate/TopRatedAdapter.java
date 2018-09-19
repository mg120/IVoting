package com.alsalil.web.vote.Rate;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alsalil.web.vote.AllRates.AllRates;
import com.alsalil.web.vote.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.alsalil.web.vote.R.drawable.my_progress;

/**
 * Created by Ma7MouD on 3/22/2018.
 */

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<TopRatedModel> list;
    private ArrayList<Integer> N_votes;
    private ArrayList<Integer> votes_list;
    private int rate_sum ;

    private String [] prog_colors = {"#1067AA", "#F1B310", "#01A793", "#622467", "#B2045B", "#ED6B20" , "#7AAC4B", "#9B26AF", "#68EFAD", "#FF7043"};

    public TopRatedAdapter(Context context, ArrayList<TopRatedModel> list, ArrayList<Integer> n_votes, ArrayList<Integer> votes_list, int rate_sum) {
        this.context = context;
        this.list = list;
        N_votes = n_votes;
        this.votes_list = votes_list;
        this.rate_sum = rate_sum;
    }

    @Override
    public TopRatedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_rated_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(TopRatedAdapter.ViewHolder holder, int position) {

        holder.progress_text.setMax(100);
        holder.progress_text.setProgress(N_votes.get(position));
        holder.progress_text.setProgressTintList(ColorStateList.valueOf(Color.parseColor(prog_colors[position])));
        holder.name.setText(list.get(position).getItem_title());
        Picasso.with(context).load("http://vote4favorite.com/vote/public/images/" + list.get(position).getItem_image()).into(holder.image);
        holder.number.setText(String.valueOf(votes_list.get(position)));

        // go to all rates activity ...
        holder.row_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , AllRates.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView number;
        ImageView image;
        ProgressBar progress_text ;
        LinearLayout row_layout ;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title_txt);
            number = (TextView) itemView.findViewById(R.id.number);
            image = (ImageView) itemView.findViewById(R.id.item_img);
            progress_text = (ProgressBar) itemView.findViewById(R.id.progress);
            row_layout = (LinearLayout) itemView.findViewById(R.id.row_layout);

        }
    }
}
