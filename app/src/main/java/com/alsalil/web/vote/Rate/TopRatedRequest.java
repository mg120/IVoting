package com.alsalil.web.vote.Rate;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ma7MouD on 3/1/2018.
 */

public class TopRatedRequest extends StringRequest {

    private static final String top_rated_url ="http://vote4favorite.com/vote/api/top-rate";
    private Map<String, String> params ;

    public TopRatedRequest( String catId, Response.Listener<String> listener) {
        super(Method.POST, top_rated_url, listener, null);

        params = new HashMap<>();
        params.put("catId" , catId);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
