package com.alsalil.web.vote.Requests;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.alsalil.web.vote.Payment;
import com.alsalil.web.vote.R;
import com.alsalil.web.vote.TermsAndConditions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SignUp extends AppCompatActivity {

    public static final int PICK_IMAGE = 100;
    private ImageView back_img;
    private EditText userName_et, email_et, pass_et, phone_et;
    private CheckBox checkBox;
    private Button signUP_btn;
    private TextView login_txt, sign_type, terms_txt;
    private ImageView user_image;
    private Bitmap bitmap;
    private int voting_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");

        back_img = (ImageView) findViewById(R.id.back);
        userName_et = (EditText) findViewById(R.id.userName_et);
        userName_et.setTypeface(custom_font);
        email_et = (EditText) findViewById(R.id.email_et);
        email_et.setTypeface(custom_font);
        pass_et = (EditText) findViewById(R.id.password_et);
        pass_et.setTypeface(custom_font);
        phone_et = (EditText) findViewById(R.id.phone_et);
        phone_et.setTypeface(custom_font);
        signUP_btn = (Button) findViewById(R.id.signUp_btn);
        login_txt = (TextView) findViewById(R.id.logIn_txt);
        login_txt.setTypeface(custom_font);
        sign_type = (TextView) findViewById(R.id.sign_type);
        sign_type.setTypeface(custom_font);
        user_image = (ImageView) findViewById(R.id.user_img);
        checkBox = (CheckBox) findViewById(R.id.ch_box);
        terms_txt = (TextView) findViewById(R.id.terms);
        terms_txt.setTypeface(custom_font);


        if (LogIn.type == 1){
            sign_type.setText("Free SignUp");
            sign_type.setTextSize(28f);
        } else if (LogIn.type == 2){
            sign_type.setText("MemberShip SignUp");
            sign_type.setTextSize(26f);
        }

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, LogIn.class));
                finish();
            }
        });
        terms_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, TermsAndConditions.class));
            }
        });

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        signUP_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register() {
        // check edit texts ....
        // final int[] type_value = {-1};

        String userName = userName_et.getText().toString().trim();
        String email = email_et.getText().toString().trim();
        String password = pass_et.getText().toString().trim();
        String phone = phone_et.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            userName_et.setError("UserName is required");
            userName_et.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            email_et.setError("email is required");
            email_et.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            phone_et.setError("password is required");
            phone_et.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            pass_et.setError("password is required");
            pass_et.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_et.setError("enter valid email");
            email_et.requestFocus();
            return;
        }

        if (!checkBox.isChecked()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
            builder.setMessage("You should approve the Terms first..")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).create()
                    .show();

            return;
        }

        //converting image to base64 string
        String imageString = "";
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }


        final ProgressDialog progDailog = new ProgressDialog(SignUp.this);
        progDailog.setMessage("Please wait .... ");
        progDailog.setProgress(0);
        progDailog.setMax(30);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress <= 30) {
                    try {
                        progDailog.setProgress(progress);
                        progress++;
                        Thread.sleep(300);
                    } catch (Exception e) {

                    }
                }
                progDailog.dismiss();
                progDailog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                        builder.setMessage("Registered Successfully")
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if (LogIn.type == 1) {
                                            finish();
                                        } else if (LogIn.type == 2){
                                            Intent intent = new Intent(SignUp.this, Payment.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                })
                                .create()
                                .show();
                    }
                });
            }
        });
        thread.start();
        progDailog.show();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    int user_id = jsonObject.getInt("userId");
                    Toast.makeText(SignUp.this, status + "\n"+ user_id ,Toast.LENGTH_SHORT).show();
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        status = jsonObject.getInt("flag");
//                        Toast.makeText(SignUp.this, status + "" , Toast.LENGTH_SHORT).show()
//                    }

                    //Toast.makeText(SignUp.this, status + "" , Toast.LENGTH_SHORT).show();

                    if (status) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
//                        builder.setMessage("Register Failed")
//                                .setCancelable(false)
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.cancel();
//                                    }
//                                })
//                                .create()
//                                .show();
//
//                    } else if (status == 1) {
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
//                        builder.setMessage("Registered Successfully")
//                                .setCancelable(false)
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.cancel();
//                                        finish();
//                                    }
//                                })
//                                .create()
//                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SignupRequest signupRequest = new SignupRequest(userName, email, phone, password, LogIn.type, imageString, listener);
        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
        queue.add(signupRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            //TODO: action
            Uri file_path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), file_path);
                user_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
