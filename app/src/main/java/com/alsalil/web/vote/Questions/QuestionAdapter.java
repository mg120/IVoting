package com.alsalil.web.vote.Questions;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alsalil.web.vote.MainActivity;
import com.alsalil.web.vote.R;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ma7MouD on 3/28/2018.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    Context context;
    ArrayList<QuestionModel> list;
    int ques_id;

    public QuestionAdapter(Context context, ArrayList<QuestionModel> list, int ques_id) {
        this.context = context;
        this.list = list;
        this.ques_id = ques_id;
    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.question_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder holder, int position) {

        Picasso.with(context).load("http://vote4favorite.com/vote/public/images/" + list.get(position).getQues_item_img()).into(holder.item_img);
        holder.item_title.setText(list.get(position).getQues_item_title());
        holder.ques_card_item.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView item_img;
        private TextView item_title;
        private CardView ques_card_item;

        public ViewHolder(View itemView) {
            super(itemView);

            ques_card_item = (CardView) itemView.findViewById(R.id.ques_item_card);
            item_img = (ImageView) itemView.findViewById(R.id.ques_item_img);
            item_title = (TextView) itemView.findViewById(R.id.ques_item_title);

            ques_card_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = (int) view.getTag();
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean status = jsonObject.getBoolean("status");
                        if (status) {
                            String message = jsonObject.getString("message");

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage(message)
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            dialogInterface.cancel();
                                        }
                                    }).create().show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("You answered before")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            dialogInterface.cancel();
                                        }
                                    }).create().show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            SubmitAns_Request ans_request = new SubmitAns_Request(MainActivity.user_id, ques_id, list.get(position).getQues_answer_id(), listener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(ans_request);
        }
    }
}
