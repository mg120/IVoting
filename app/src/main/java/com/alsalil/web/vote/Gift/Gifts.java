package com.alsalil.web.vote.Gift;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.alsalil.web.vote.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Gifts extends AppCompatActivity {

    private GiftAdapter adapter ;
    private ArrayList<GiftModel> gift_data = new ArrayList<>();
    private String gift_url = "http://vote4favorite.com/vote/api/gift";
    private TextView back ;
    private ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        listView = (ListView) findViewById(R.id.gift_list);
        back = (TextView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        StringRequest request = new StringRequest(Request.Method.POST, gift_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    giftData(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Gifts.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void giftData(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);

        int status = jsonObject.getInt("status");

        if (status == 1){

            JSONArray gift_data_arr = jsonObject.getJSONArray("gifts");
            for (int i=0; i<gift_data_arr.length();i++){
                JSONObject data_obj = gift_data_arr.getJSONObject(i);
                String name = data_obj.getString("name");
                String details = data_obj.getString("details");
                String points = data_obj.getString("points");
                String image = data_obj.getString("imageName");

                gift_data.add(new GiftModel(image, name, details, points));
            }

            adapter = new GiftAdapter(Gifts.this, gift_data);
            listView.setAdapter(adapter);
        }
        else {
            Toast.makeText(Gifts.this, "Error ", Toast.LENGTH_SHORT).show();
        }

    }
}

