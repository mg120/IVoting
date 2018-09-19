package com.alsalil.web.vote;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Payment extends AppCompatActivity {

    TextView back;
    EditText credit_name_et, credit_number_et, exp_et, security_code_et;
    Button confirm_payment;
    ImageView visa_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        back = (TextView) findViewById(R.id.back);
        credit_name_et = (EditText) findViewById(R.id.credit_card_name_et);
        credit_number_et = (EditText) findViewById(R.id.credit_card_num_et);
        exp_et = (EditText) findViewById(R.id.exp_date_et);
        security_code_et = (EditText) findViewById(R.id.security_code_et);
        confirm_payment = (Button) findViewById(R.id.payment_btn);
        visa_payment = (ImageView) findViewById(R.id.visa_payment);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        visa_payment.setImageResource(R.drawable.visa);
        confirm_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Payment.this);
                builder.setMessage("sorry, waiting for payment API")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).create()
                        .show();
            }
        });
    }
}
