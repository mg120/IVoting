package com.alsalil.web.vote.Profile;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ma7MouD on 2/27/2018.
 */

public class ProfileRequest extends StringRequest {

    private static final String profile_url = "http://vote4favorite.com/vote/api/profile";
    private Map<String , String> params ;

    public ProfileRequest(String user_id, Response.Listener<String> listener) {
        super(Method.POST, profile_url, listener, null);

        params = new HashMap<>();

        params.put("userId", user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
