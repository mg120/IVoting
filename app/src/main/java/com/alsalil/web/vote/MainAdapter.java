package com.alsalil.web.vote;

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

import com.alsalil.web.vote.Section.Section;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ma7MouD on 3/22/2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Context context ;
    ArrayList<MainModel> list ;
    ArrayList<MainModel> arrayList ;
    int lastPosition = -1;

    public MainAdapter(Context context, ArrayList<MainModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, final int position) {

        // bind data to  items of recyclerview ....
        Picasso.with(context).load("http://vote4favorite.com/vote/public/images/" + list.get(position).getImage_id()).into(holder.imageView);
        holder.title.setText(list.get(position).getTitle());
        holder.cardView.setTag(position);

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView ;
        TextView title ;
        CardView cardView ;
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/font.ttf");

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.recycler_image);
            title = (TextView) itemView.findViewById(R.id.img_title);
            title.setTypeface(custom_font);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();

            //Toast.makeText(context, ""+getPosition(),Toast.LENGTH_SHORT).show();
            // Toast.makeText(context, list.get(getPosition()).getTitle(),Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, Section.class);
            intent.putExtra("catName", list.get(position).getTitle());
            intent.putExtra("secImage", list.get(position).getImage_id());
            intent.putExtra("catId" , list.get(position).getCat_Id());
            context.startActivity(intent);
        }
    }


    public void setfilter(ArrayList<MainModel> newlist){

        arrayList = new ArrayList<>();
        arrayList.addAll(newlist);
        notifyDataSetChanged();

    }

}
