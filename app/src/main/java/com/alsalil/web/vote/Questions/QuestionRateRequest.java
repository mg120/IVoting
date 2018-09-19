package com.alsalil.web.vote.Questions;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

/**
 * Created by Ma7MouD on 3/29/2018.
 */

public class QuestionRateRequest extends StringRequest {

    private static final String ans_value_url = "http://vote4favorite.com/vote/api/count-ans";
    private HashMap<String, String> params ;

    public QuestionRateRequest(String ques_id, Response.Listener<String> listener) {
        super(Method.POST, ans_value_url, listener, null);

        params = new HashMap<>();
        params.put("question_id" , ques_id );
    }

    @Override
    public HashMap<String, String> getParams() {
        return params;
    }
}
