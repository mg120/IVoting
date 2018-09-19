package com.alsalil.web.vote;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact extends AppCompatActivity {

    private String contact_url = "http://vote4favorite.com/vote/api/contact";
    TextView back;
    TextView email, facebook, linked, twiter, phone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");

        back = (TextView) findViewById(R.id.back);
        email = (TextView) findViewById(R.id.email_txt);
        email.setTypeface(custom_font);
        facebook = (TextView) findViewById(R.id.facebook_txt);
        facebook.setTypeface(custom_font);
        linked = (TextView) findViewById(R.id.linkedIn_txt);
        linked.setTypeface(custom_font);
        twiter = (TextView) findViewById(R.id.twitter_txt);
        twiter.setTypeface(custom_font);
        phone = (TextView) findViewById(R.id.phone_txt);
        phone.setTypeface(custom_font);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST, contact_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");

                            if (status){
                                JSONObject data_obj = jsonObject.getJSONObject("message");
                                String email_txt =data_obj.getString("email");
                                email.setText("Email:  " +email_txt);
                                String facebook_txt =data_obj.getString("facebook");
                                facebook.setText("FaceBook:  "+facebook_txt);
                                String twitter_txt =data_obj.getString("twitter");
                                twiter.setText("Twitter:  "+twitter_txt);
                                String linkedin_txt =data_obj.getString("linkedin");
                                linked.setText("LinkedIn:  " +linkedin_txt);
                                String phone1_txt =data_obj.getString("phone1");
                                phone.setText("PHONE: " +phone1_txt);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Contact.this, "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(Contact.this);
        queue.add(stringRequest);

    }
}
