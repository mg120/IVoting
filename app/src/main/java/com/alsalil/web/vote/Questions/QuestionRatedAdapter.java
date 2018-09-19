package com.alsalil.web.vote.Questions;

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
import com.alsalil.web.vote.Rate.Rate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ma7MouD on 3/28/2018.
 */

public class QuestionRatedAdapter extends RecyclerView.Adapter<QuestionRatedAdapter.ViewHolder>{

    private Context context ;
    private ArrayList<QuestionModel> list ;
    private ArrayList<Integer> ans_rate_list ;
    private ArrayList<Integer> N_votes ;

    private String [] prog_colors = {"#1067AA", "#F1B310", "#01A793", "#622467", "#B2045B", "#ED6B20" , "#7AAC4B", "#9B26AF", "#68EFAD", "#FF7043"};

    public QuestionRatedAdapter(Context context, ArrayList<QuestionModel> list, ArrayList<Integer> ans_rate_list, ArrayList<Integer> n_votes) {
        this.context = context;
        this.list = list;
        this.ans_rate_list = ans_rate_list;
        N_votes = n_votes;
    }

    @Override
    public QuestionRatedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ques_rated_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(QuestionRatedAdapter.ViewHolder holder, int position) {

        holder.ques_rated_item_prog.setMax(100);
        holder.ques_rated_item_prog.setProgressTintList(ColorStateList.valueOf(Color.parseColor(prog_colors[position])));
        holder.ques_rated_item_prog.setProgress(N_votes.get(position));
        holder.ques_rated_count_num.setText(String.valueOf(ans_rate_list.get(position)));
        Picasso.with(context).load("http://vote4favorite.com/vote/public/images/" + list.get(position).getQues_item_img()).into(holder.ques_rated_item_img);
        holder.ques_rated_item_title.setText(list.get(position).getQues_item_title());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ques_rated_item_title ;
        TextView ques_rated_count_num ;
        ImageView ques_rated_item_img ;
        ProgressBar ques_rated_item_prog ;

        public ViewHolder(View itemView) {
            super(itemView);

            ques_rated_count_num = (TextView) itemView.findViewById(R.id.ques_number);
            ques_rated_item_title = (TextView) itemView.findViewById(R.id.ques_title_txt);
            ques_rated_item_img = (ImageView) itemView.findViewById(R.id.ques_item_img);
            ques_rated_item_prog = (ProgressBar) itemView.findViewById(R.id.ques_progress);
        }
    }
}
