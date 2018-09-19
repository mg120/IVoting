package com.alsalil.web.vote.Section;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ma7MouD on 2/26/2018.
 */

public class SectionRequest extends StringRequest {

    private static final String section_url = "http://vote4favorite.com/vote/api/section";
    Map<String, String> params ;

    public SectionRequest(String catName, Response.Listener<String> listener) {
        super(Method.POST, section_url, listener, null);

        params = new HashMap<>();
        params.put("catName" , catName);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
