package com.alsalil.web.vote.Rate;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ma7MouD on 2/26/2018.
 */

public class AddRateRequest extends StringRequest {

    private static final String rate_url = "http://vote4favorite.com/vote/api/addRate";
    private Map<String, String> params ;

    public AddRateRequest(String user_id, String item_id, String cat_id, int rate, Response.Listener<String> listener) {
        super(Method.POST, rate_url, listener, null);

        params = new HashMap<>();

        params.put("userId", user_id);
        params.put("itemId", item_id);
        params.put("catId", cat_id);
        params.put("rate", rate + "");
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
