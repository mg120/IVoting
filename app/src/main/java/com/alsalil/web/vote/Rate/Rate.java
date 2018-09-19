package com.alsalil.web.vote.Rate;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.alsalil.web.vote.MainActivity;
import com.alsalil.web.vote.R;
import com.alsalil.web.vote.Section.Section;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Rate extends AppCompatActivity {

    private TextView back;
    private TextView item_name, votes_number, category_txt;
    private ImageView imageView, voting_image;
    private RecyclerView recyclerView;

    private ArrayList<Integer> votes_list = new ArrayList<>();
    private ArrayList<TopRatedModel> topRatedModels = new ArrayList<>();
    private int item_votes = 0;
    private int voting = 0;
    private String item_id;
    private static final String rated_before_url = "http://vote4favorite.com/vote/api/rated/before";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");

        // intent Data ....
        Intent intent = getIntent();
        String name = intent.getExtras().getString("item_title");
        String image = intent.getExtras().getString("item_image");
        String catName = intent.getExtras().getString("catName");
        item_id = intent.getExtras().getString("item_id");
        // viewss reference object  ...
        item_name = (TextView) findViewById(R.id.item_name);
        item_name.setTypeface(custom_font);
        imageView = (ImageView) findViewById(R.id.item_img);
        voting_image = (ImageView) findViewById(R.id.vote_img);
        //votes_number = (TextView) findViewById(R.id.votes_num);
        back = (TextView) findViewById(R.id.back);
        recyclerView = (RecyclerView) findViewById(R.id.top_rated_recycler);
        category_txt = (TextView) findViewById(R.id.category_top_txt);
        category_txt.setTypeface(custom_font);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        category_txt.setText(catName + " :");
        item_name.setText(name);
        Picasso.with(this).load("http://vote4favorite.com/vote/public/images/" + image).into(imageView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // rated before method ....
        ratedBefore();


        voting_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (voting == 0) {
                    // votes_number.setText(""+ item_votes++);
                    voting = 1;
                } else {
                    // votes_number.setText(""+ item_votes--);
                    voting = 0;
                }


                // Toast.makeText(Rate.this, ""+ votes_number.getText(),Toast.LENGTH_SHORT).show();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            int status = jsonObject.getInt("status");

//                            Log.e("voting", "" + voting);
//                            Log.e("status", status + "");
//

                            if (status == 1) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(Rate.this);
                                builder.setMessage("Voting Successfully")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                ratedBefore();
                                                // recyclerView.setAdapter(null);
                                                // top_rate_data();
                                                dialogInterface.cancel();
                                            }
                                        }).create()
                                        .show();
                            } else if (status == 2) {

                                ratedBefore();

                            } else if (status == 3) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Rate.this);
                                builder.setMessage("plz vote for another category")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        }).create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AddRateRequest rateRequest = new AddRateRequest(MainActivity.user_id, item_id, Section.cat_Id, voting, listener);
                RequestQueue queue = Volley.newRequestQueue(Rate.this);
                queue.add(rateRequest);
            }
        });

        // connect with service to get top rated recycler data ....
        top_rate_data();
    }

    private void ratedBefore() {
        StringRequest rated_before_request = new StringRequest(Request.Method.POST, rated_before_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int rated_before = jsonObject.getInt("status");
                    Log.e("rated_before", rated_before + "");

                    if (rated_before == 1) {
                        voting_image.setImageResource(R.drawable.like_full_blue);
                    } else {
                        voting_image.setImageResource(R.drawable.dislike);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Rate.this);
                builder.setMessage("Error Connection")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create()
                        .show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userId", MainActivity.user_id);
                params.put("itemId", item_id);
                params.put("catId", Section.cat_Id);
                params.put("rate", voting + "");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(rated_before_request);

    }

    /// recycler top rated data ....
    private void top_rate_data() {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    //Toast.makeText(Rate.this, status + "" , Toast.LENGTH_SHORT).show();
                    if (status == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("rates");
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                int item_Id = jsonObject1.getInt("itemId");
                                String item_name = jsonObject1.getString("itemName");
                                String item_image = jsonObject1.getString("imageName");
                                int rate_count = jsonObject1.getInt("rate_count");

                                //Toast.makeText(Rate.this, item_name+"\n"+ item_image, Toast.LENGTH_SHORT).show();
                                votes_list.add(rate_count);
                                topRatedModels.add(new TopRatedModel(item_name, item_image));

                                // Toast.makeText(Rate.this, String.valueOf(topRatedModels) + "" , Toast.LENGTH_SHORT).show();
                            }
                        }
                        //max_vote = Collections.max(votes_list);
                        Collections.sort(votes_list, Collections.reverseOrder());
                        int rate_sum = 0;
                        ArrayList<Integer> N_votes = new ArrayList<>();
                        for (int i = 0; i < votes_list.size(); i++) {

                            rate_sum+= votes_list.get(i);
                        }

                        for (int y=0;y<votes_list.size(); y++){
                            int rate_percent = (int) (((double) votes_list.get(y) / (double) rate_sum) * 100);
                            N_votes.add(rate_percent);
                        }

                        TopRatedAdapter adapter = new TopRatedAdapter(Rate.this, topRatedModels,N_votes ,votes_list , rate_sum);
                        recyclerView.setAdapter(adapter);

                    } else if (status == 0) {
                        // Toast.makeText(Rate.this, "Error" , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        TopRatedRequest topRatedRequest = new TopRatedRequest(Section.cat_Id, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(topRatedRequest);
    }
}
