package com.alsalil.web.vote;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TermsAndConditions extends AppCompatActivity {

    private static final String terms_url = "http://vote4favorite.com/vote/api/setting";
    private TextView textView, back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");

        textView = (TextView) findViewById(R.id.terms_textV);
        textView.setTypeface(custom_font);
        back = (TextView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        StringRequest request = new StringRequest(Request.Method.POST, terms_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 1){

                        JSONObject jsonObject1 = jsonObject.getJSONObject("setting");
                        String policy = jsonObject1.getString("policy");
                        String N_policy = String.valueOf(Html.fromHtml(policy));

                        textView.setText(N_policy);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }

}
