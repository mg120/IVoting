package com.alsalil.web.vote.Requests;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.alsalil.web.vote.MainActivity;
import com.alsalil.web.vote.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogIn extends AppCompatActivity {

    private EditText email_et, pass_et;
    private Button login;
    private TextView signUp_txt;
    private String email;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    private String password;
    public static int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");

//        Intent intent = getIntent();
//        type = intent.getExtras().getInt("type");
        //Toast.makeText(this, type+ "", Toast.LENGTH_SHORT).show();
        //Log.e("type", type + "");

        email_et = (EditText) findViewById(R.id.email);
        email_et.setTypeface(custom_font);
        pass_et = (EditText) findViewById(R.id.password);
        pass_et.setTypeface(custom_font);
        login = (Button) findViewById(R.id.login_btn);
        signUp_txt = (TextView) findViewById(R.id.signUp_txt);
        signUp_txt.setTypeface(custom_font);
        sharedPreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        email_et.setText(sharedPreferences.getString("email", null));
        pass_et.setText( sharedPreferences.getString("password", null));

        login();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check mail and password ....
                // now we have valid email and password ...
                // Save Email and Password in shared preferences ...
                //SharedPreferences.Editor editor = get
                login();


            }
        });


        signUp_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this, SignUp.class));
            }
        });
    }

    public void login() {
        initialize();
        if (!validate()) {
        } else {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        String user_id = "";
                        String email = "";
                        String user_name = "";
                        String user_image = "";

                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");

                         //Toast.makeText(LogIn.this, status + "", Toast.LENGTH_SHORT).show();
                        if (status == 1) {
                            JSONArray userData = jsonObject.getJSONArray("userData");
                            for (int i = 0; i < userData.length(); i++) {

                                JSONObject data_obj = userData.getJSONObject(i);
                                user_id = data_obj.getString("id");
                                Log.e("user_id :", user_id);
                                email = data_obj.getString("email");
                                Log.e("email :", email);
                                user_name = data_obj.getString("userName");
                                user_image = data_obj.getString("imageName");
                                type = data_obj.getInt("type");

                               // Toast.makeText(LogIn.this, type + "",Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(LogIn.this, MainActivity.class);
                            intent.putExtra("user_id", user_id);
                            intent.putExtra("email", email);
                            intent.putExtra("userName", user_name);
                            intent.putExtra("imageName", user_image);
                            startActivity(intent);

                            // open Main Activity ....7
                            Toast.makeText(LogIn.this, "تم الدخول بنجاح", Toast.LENGTH_SHORT).show();
                            //finish();

                        } else if (status == 2) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
                            builder.setTitle("sorry, waiting for payment API")
                                    .setCancelable(false);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {


                            AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
                            builder.setTitle("Error email or password")
                                    .setCancelable(false);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(email, password, listener);
            RequestQueue queue = Volley.newRequestQueue(LogIn.this);
            queue.add(loginRequest);
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (TextUtils.isEmpty(email)) {
            email_et.setError("Email Required");
            email_et.requestFocus();
            valid = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_et.setError("enter valid email");
            email_et.requestFocus();
            valid = false;
        }

        if (TextUtils.isEmpty(password)) {
            pass_et.setError("Password required");
            pass_et.requestFocus();
            valid = false;
        }

        return valid;
    }

    private void initialize() {
        email = email_et.getText().toString().trim();
        password = pass_et.getText().toString().trim();

    }

    @Override
    protected void onPause() {
        super.onPause();
        editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();
        editor.apply();
    }
}
