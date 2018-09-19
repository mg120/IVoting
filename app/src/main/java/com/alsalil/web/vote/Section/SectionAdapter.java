package com.alsalil.web.vote.Section;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alsalil.web.vote.MainAdapter;
import com.alsalil.web.vote.R;
import com.alsalil.web.vote.Rate.Rate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ma7MouD on 3/22/2018.
 */

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {

    Context mcontext ;
    ArrayList<SectionModel> section_data ;
    String catName ;
    int lastPosition = -1;

    public SectionAdapter(Context mcontext, ArrayList<SectionModel> section_data, String catName) {
        this.mcontext = mcontext;
        this.section_data = section_data;
        this.catName = catName;
    }

    @Override
    public SectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.section_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SectionAdapter.ViewHolder holder, final int position) {

        // put data to item_image and item_title ...
        Picasso.with(mcontext).load("http://vote4favorite.com/vote/public/images/" +section_data.get(position).getSection_item_img()).into(holder.sec_item_img);
        holder.sec_item_title.setText(section_data.get(position).getSection_item_title());

        Animation animation = AnimationUtils.loadAnimation(mcontext,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // need book name and its image to rate activity ...
                Intent intent = new Intent(mcontext, Rate.class);
                intent.putExtra("item_title", section_data.get(position).getSection_item_title());
                intent.putExtra("item_image", section_data.get(position).getSection_item_img());
                intent.putExtra("item_id" , section_data.get(position).getItem_id());
                intent.putExtra("catName" , catName);
                // Toast.makeText(mcontext, section_data.get(position).getSection_item_img(),Toast.LENGTH_SHORT).show();
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return section_data.size();
    }

    @Override
    public void onViewDetachedFromWindow(SectionAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView sec_item_img ;
        TextView sec_item_title;
        CardView cardView ;

        public ViewHolder(View itemView) {
            super(itemView);

            Typeface custom_font = Typeface.createFromAsset(mcontext.getAssets(), "fonts/font.ttf");

            cardView = (CardView) itemView.findViewById(R.id.sectin_card_item);
            sec_item_img = (ImageView) itemView.findViewById(R.id.section_item_img);
            sec_item_title = (TextView) itemView.findViewById(R.id.section_item_title);
            sec_item_title.setTypeface(custom_font);
        }
    }
}
