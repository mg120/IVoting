package com.alsalil.web.vote.Questions;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alsalil.web.vote.MainActivity;
import com.alsalil.web.vote.R;
import com.alsalil.web.vote.Section.Section;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Question extends AppCompatActivity {


    private String ques_data_url = "http://vote4favorite.com/vote/api/questions";
    String url = "http://vote4favorite.com/vote/api/count-ans";

    private RecyclerView ques_recycler, rated_recycler;
    private RecyclerView.LayoutManager layoutManager;
    private QuestionAdapter adapter;
    RequestQueue requestQueue;
    private TextView ques_text;
    private TextView back;
    ArrayList<QuestionModel> ques_list = new ArrayList<>();
    ArrayList<Integer> answers_rate_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ques_text = (TextView) findViewById(R.id.ques_txt);
        ques_recycler = (RecyclerView) findViewById(R.id.ques_recycler);
        rated_recycler = (RecyclerView) findViewById(R.id.ques_rating);
        layoutManager = new GridLayoutManager(Question.this, 3);
        ques_recycler.setLayoutManager(layoutManager);
        ques_recycler.setHasFixedSize(true);

        StringRequest request = new StringRequest(Request.Method.POST, ques_data_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    items_parsing(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Question.this);
                builder.setMessage("Error Connection")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(Question.this);
        queue.add(request);

        //Toast.makeText(this, "mmmmm", Toast.LENGTH_SHORT).show();

    }


    private void items_parsing(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        boolean status = jsonObject.getBoolean("status");

        if (status) {

            JSONObject data_obj = jsonObject.getJSONObject("data");
            JSONObject ques_obj = data_obj.getJSONObject("question");
            int ques_id = ques_obj.getInt("id");
            String question = ques_obj.getString("question");
            ques_text.setText(question);

            // Toast.makeText(Question.this, "" + ques_id, Toast.LENGTH_SHORT).show();

            // get data Answers ...
            JSONArray answers_arr = data_obj.getJSONArray("answers");
            for (int i = 0; i < answers_arr.length(); i++) {
                JSONObject answer_obj = answers_arr.getJSONObject(i);
                int answer_id = answer_obj.getInt("id");
                String answer = answer_obj.getString("answer");
                String answer_img = answer_obj.getString("image");

                ques_list.add(new QuestionModel(answer_id, answer_img, answer));
            }

            if (ques_list != null) {
                mmm(ques_id);
                adapter = new QuestionAdapter(Question.this, ques_list, ques_id);
                ques_recycler.setAdapter(adapter);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Question.this);
                builder.setMessage("No Data Found, check Internet..")
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
    }

    public void mmm(final int ques_id) {
        // Toast.makeText(Question.this, "" + ques_id, Toast.LENGTH_SHORT).show();
        requestQueue = Volley.newRequestQueue(Question.this);

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject mainObject = new JSONObject(response);

                    for (int i = 0; i < ques_list.size(); i++) {
                        int id = mainObject.getInt(String.valueOf(ques_list.get(i).getQues_answer_id()));
                        answers_rate_list.add(id);
                    }

                    Collections.sort(answers_rate_list, Collections.reverseOrder());
                    int rate_sum = 0;
                    ArrayList<Integer> N_votes = new ArrayList<>();
                    for (int i = 0; i < answers_rate_list.size(); i++) {

                        rate_sum+= answers_rate_list.get(i);
                    }

                    for (int y=0;y<answers_rate_list.size(); y++){
                        int rate_percent = (int) (((double) answers_rate_list.get(y) / (double) rate_sum) * 100);
                        N_votes.add(rate_percent);
                    }

                    if (answers_rate_list != null) {
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Question.this);
                        rated_recycler.setLayoutManager(layoutManager);
                        rated_recycler.setHasFixedSize(true);
                        QuestionRatedAdapter questionRatedAdapter = new QuestionRatedAdapter(Question.this, ques_list, answers_rate_list, N_votes);
                        rated_recycler.setAdapter(questionRatedAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        QuestionRateRequest questionRateRequest = new QuestionRateRequest(String.valueOf(ques_id), stringListener);
        RequestQueue queue = Volley.newRequestQueue(Question.this);
        queue.add(questionRateRequest);

    }
}
