package com.alsalil.web.vote.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ma7MouD on 2/15/2018.http://vote4fa
 */

public class LoginRequest extends StringRequest {

    private static final String login_url = "http://vote4favorite.com/vote/api/login";
    private Map<String, String> params ;

    public LoginRequest(String email, String password, Response.Listener<String> listener) {
        super(Method.POST, login_url , listener, null);

        params = new HashMap<>();
        params.put("email" , email);
        params.put("password" , password);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
