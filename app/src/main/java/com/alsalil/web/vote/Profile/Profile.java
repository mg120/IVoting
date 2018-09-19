package com.alsalil.web.vote.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.alsalil.web.vote.MainActivity;
import com.alsalil.web.vote.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Profile extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    private TextView back, user_id;
    private EditText userName, email_et, phone_et, password_et;
    private ImageView profile_image;
    private TextView type_txt;
    private Button update_profile;
    private Bitmap bitmap;
    private String image_url ;
    private String encoding = "" ;
    private String password = "" ;
    private int voting_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        String[] types = getResources().getStringArray(R.array.type);

        user_id = (TextView) findViewById(R.id.user_id);
        user_id.setTypeface(custom_font);
        back = (TextView) findViewById(R.id.back);
        userName = (EditText) findViewById(R.id.userName_et);
        userName.setTypeface(custom_font);
        email_et = (EditText) findViewById(R.id.email_et);
        email_et.setTypeface(custom_font);
        password_et = (EditText) findViewById(R.id.password_et);
        password_et.setTypeface(custom_font);
        profile_image = (ImageView) findViewById(R.id.profile_img);
        type_txt = (TextView) findViewById(R.id.type_txt);
        update_profile = (Button) findViewById(R.id.update_profile);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(Profile.this, android.R.layout.simple_dropdown_item_1line, types);
       // spinner.setAdapter(adapter);
        final Response.Listener<String> pro_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        JSONObject data_obj = jsonObject.getJSONObject("userData");
                        // Toast.makeText(Profile.this, "" + status, Toast.LENGTH_SHORT).show();
                        user_id.setText("Id: " + data_obj.getString("id"));
                        userName.setText(data_obj.getString("userName"));
                        email_et.setText(data_obj.getString("email"));
                        String type = data_obj.getString("type");
                        image_url = "http://vote4favorite.com/vote/storage/app/images/" + data_obj.getString("imageName");
                        Picasso.with(Profile.this).load(image_url).into(profile_image);
                        // Toast.makeText(Profile.this , image_url , Toast.LENGTH_SHORT).show();

                        if (type.equals("1")) {
                            voting_type = 1;
                            type_txt.setText("Type: Free MemberShip");
                        } else if (type.equals("2")) {
                            voting_type = 2;
                            type_txt.setText("Type: Premium MemberShip");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        final ProfileRequest request = new ProfileRequest(MainActivity.user_id, pro_listener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = userName.getText().toString();
                String user_email = email_et.getText().toString();
                password = password_et.getText().toString();
                String id = user_id.getText().toString();
//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//
//                        String selected_item = adapterView.getItemAtPosition(position).toString();
//                        if (selected_item.equals("Free Membership")) {
//
//                            voting_type = 1;
//                        } else if (selected_item.equals("Premium Membership")) {
//
//                            voting_type = 2;
//                        } else {
//                            voting_type = 0;
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });

                Response.Listener<String> update_listner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("success");

                            if (status == 1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                                builder.setMessage("Profile Updated Successfully")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        }).create()
                                        .show();
                            } else if (status == 0) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                                builder.setMessage("Nothing Updated")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        }).create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                UpdateRequest updateRequest = new UpdateRequest(id, user_name, user_email, password, encoding, update_listner);
                RequestQueue requestQueue = Volley.newRequestQueue(Profile.this);
                requestQueue.add(updateRequest);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            //TODO: action
            Uri file_path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , file_path);
                profile_image.setImageBitmap(bitmap);

                //converting image to base64 string
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encoding = Base64.encodeToString(imageBytes, Base64.DEFAULT);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
