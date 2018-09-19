package com.alsalil.web.vote.Questions;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

/**
 * Created by Ma7MouD on 3/28/2018.
 */

public class SubmitAns_Request extends StringRequest {

    private static final String submit_ans = "http://vote4favorite.com/vote/api/submit/answer";
    private HashMap<String , String> params ;

    public SubmitAns_Request(String user_id, int ques_id, int ans_id, Response.Listener<String> listener) {
        super(Method.POST, submit_ans, listener, null);

        params = new HashMap<>();
        params.put("user_id", user_id + "");
        params.put("question_id", ques_id + "");
        params.put("answer_id", ans_id + "");
    }

    @Override
    public HashMap<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
