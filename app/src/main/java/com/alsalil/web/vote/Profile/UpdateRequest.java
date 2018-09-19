package com.alsalil.web.vote.Profile;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ma7MouD on 2/27/2018.
 */

public class UpdateRequest extends StringRequest {

    private static final String update_url = "http://vote4favorite.com/WebServices/UpdateProfile.php";
    private Map<String , String> params ;

    public UpdateRequest(String id, String user_Name, String email, String password, String  encoding, Response.Listener<String> listener) {
        super(Method.POST, update_url, listener, null);

        params = new HashMap<>();

        params.put("id", id);
        params.put("userName", user_Name);
        params.put("email",email);
        params.put("password",password);
        params.put("imageName",encoding);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
