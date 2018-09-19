package com.alsalil.web.vote.AllRates;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.alsalil.web.vote.R;
import com.alsalil.web.vote.Section.Section;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AllRates extends AppCompatActivity {

    private static final String all_rates_url = "http://vote4favorite.com/vote/api/all-vote";
    private TextView title_txt;
    private TextView back;
    private RecyclerView all_rates_recycler;
    private ArrayList<AllRatesModel> allRatesList = new ArrayList<>();
    private ArrayList<Integer> votes_count_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rates);

        all_rates_recycler = (RecyclerView) findViewById(R.id.all_rates_recycler);
        title_txt = (TextView) findViewById(R.id.all_rates_title);
        back = (TextView) findViewById(R.id.back);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        all_rates_recycler.setLayoutManager(layoutManager);
        all_rates_recycler.setHasFixedSize(true);


        title_txt.setText(Section.catName + " Items");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        StringRequest all_rates_request = new StringRequest(Request.Method.POST, all_rates_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    //Toast.makeText(AllRates.this, ""+ status, Toast.LENGTH_SHORT).show();
                    if (status == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("rates");
                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject rates_obj = jsonArray.getJSONObject(i);
                                String item_id = rates_obj.getString("itemId");
                                String itemName = rates_obj.getString("itemName");
                                String imageName = rates_obj.getString("imageName");
                                String rate_count = rates_obj.getString("rate_count");

                                allRatesList.add(new AllRatesModel(item_id, itemName, imageName));
                                votes_count_list.add(Integer.parseInt(rate_count));
                            }

                            int rate_sum = 0;
                            ArrayList<Integer> N_votes = new ArrayList<>();
                            for (int i = 0; i < votes_count_list.size(); i++) {

                                rate_sum+= votes_count_list.get(i);
                            }

                            for (int y=0;y<votes_count_list.size(); y++){
                                int rate_percent = (int) (((double) votes_count_list.get(y) / (double) rate_sum) * 100);
                                N_votes.add(rate_percent);
                            }

                            if (allRatesList != null){
                            Collections.sort(votes_count_list, Collections.reverseOrder());
                            AllRatesAdapter allRatesAdapter = new AllRatesAdapter(AllRates.this, allRatesList, votes_count_list, N_votes);
                            all_rates_recycler.setAdapter(allRatesAdapter);
                        }
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(AllRates.this);
                            builder.setMessage("There is No data ..")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    }).create()
                                    .show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AllRates.this);
                builder.setMessage("No Internet Connection ..")
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

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("catId", Section.cat_Id);
                return parameters;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(all_rates_request);
    }
}
