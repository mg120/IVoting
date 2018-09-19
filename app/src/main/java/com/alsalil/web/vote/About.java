package com.alsalil.web.vote;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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

public class About extends AppCompatActivity {

    private static final String about_url = "http://vote4favorite.com/vote/api/setting";
    private TextView about_txt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");

        TextView back = (TextView) findViewById(R.id.back);
        about_txt = (TextView) findViewById(R.id.about_txt);
        about_txt.setTypeface(custom_font);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        StringRequest request = new StringRequest(Request.Method.POST, about_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    aboutData(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(About.this , " Error connection" , Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(About.this);
        queue.add(request);
    }

    private void aboutData(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);

        int status = jsonObject.getInt("status");
        if (status == 1) {
            JSONObject data_obj = jsonObject.getJSONObject("setting");
            String about = data_obj.getString("aboutUS");
            String n_about = String.valueOf(Html.fromHtml(about));
            about_txt.setText(n_about);
        }
        else {
            Toast.makeText(About.this , "Error Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
