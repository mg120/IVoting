package com.alsalil.web.vote.Gift;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alsalil.web.vote.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ma7MouD on 3/22/2018.
 */

public class GiftAdapter extends BaseAdapter {

    Context context ;
    ArrayList<GiftModel> gift_data ;

    public GiftAdapter(Context context, ArrayList<GiftModel> gift_data) {
        this.context = context;
        this.gift_data = gift_data;
    }

    @Override
    public int getCount() {
        return gift_data.size();
    }

    @Override
    public Object getItem(int i) {
        return gift_data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/font.ttf");

        view = LayoutInflater.from(context).inflate(R.layout.gift_list_item, viewGroup, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.gift_img);
        TextView title = (TextView) view.findViewById(R.id.gift_title);
        title.setTypeface(custom_font);
        TextView description = (TextView) view.findViewById(R.id.gift_desc);
        description.setTypeface(custom_font);
        TextView points = (TextView) view.findViewById(R.id.gift_points);
        points.setTypeface(custom_font);
        Picasso.with(context).load("http://vote4favorite.com/vote/public/images/" + gift_data.get(i).getImageView_url()).into(imageView);
        title.setText(gift_data.get(i).getTitle());
        description.setText(gift_data.get(i).getDescription());
        points.setText(gift_data.get(i).getPoints_num() + "points");

        return view;
    }
}
