package com.alsalil.web.vote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alsalil.web.vote.Requests.LogIn;

public class MemberShip extends AppCompatActivity {

    private Button free_membership_btn, premium_membership;
    private TextView textView;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_ship);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");

        textView = (TextView) findViewById(R.id.textview);
        textView.setTypeface(custom_font);
        free_membership_btn = (Button) findViewById(R.id.free_membership);
        free_membership_btn.setTypeface(custom_font);
        premium_membership = (Button) findViewById(R.id.premium_membership);
        premium_membership.setTypeface(custom_font);

        free_membership_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberShip.this, LogIn.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });

        premium_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                editor = pref.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(MemberShip.this, LogIn.class);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Close App")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        MemberShip.this.finish();
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();

    }
}
