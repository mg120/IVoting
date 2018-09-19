package com.alsalil.web.vote.Requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ma7MouD on 2/15/2018.
 */

public class SignupRequest extends StringRequest {

    private static final String signUp_url = "http://vote4favorite.com/vote/api/register";
    private Map<String, String> params ;

    public SignupRequest(String userName, String email, String phone, String password, int type,String image, Response.Listener<String> listener) {
        super(Method.POST, signUp_url , listener, null);

        params = new HashMap<>();

        // put data to map here ...
        params.put("userName" , userName);
        params.put("email" , email );
        params.put("phone" , phone);
        params.put("password" , password);
        params.put("type" , type + "");
        params.put("imageName" , image);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
