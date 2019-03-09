package com.android.khoog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nispok.snackbar.Snackbar;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Verification extends AppCompatActivity {

    String code;


    ProgressDialog pgDialog;

    FirebaseAuth mAuth;
    PinView pinEntry;
    @BindView(R.id.mainTitle)
    TextView title;
    @BindView(R.id.subtext)
    TextView subtxt;

    @BindView(R.id.aviVerify)
    AVLoadingIndicatorView avVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        Intent in = getIntent();
        code = in.getStringExtra("code");
        title.setTypeface(boldfont());
        subtxt.setTypeface(boldfont());
        mAuth = FirebaseAuth.getInstance();
        pinEntry = (PinView) findViewById(R.id.view);

        ImageView backarr;
        backarr=(ImageView)findViewById(R.id.backarrow);
        backarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Verification.this,RegisterUser.class);
                startActivity(intent);
                finish();
            }
        });


        pinEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                int count = editable.length();

                if (count < 6)
                {
                    pinEntry.setLineColor(Color.parseColor("#F9AD19"));

                }else{

                    pgDialog = new ProgressDialog(Verification.this);
                    pgDialog.setMessage("Please wait...");
                    pgDialog.show();
                    pgDialog.setCanceledOnTouchOutside(false);

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(code,pinEntry.getText().toString());
                    signIn(phoneAuthCredential);


                }

            }
        });


    }


    public void signIn(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {

                    if (pgDialog.isShowing())
                    {
                        pgDialog.dismiss();
                    }

                    Intent intent = new Intent(Verification.this, LoginActivity.class);
                    startActivity(intent);

                    avVerify.setVisibility(View.GONE);

                }else{


                    if (pgDialog.isShowing())
                    {
                        pgDialog.dismiss();
                    }
                    pinEntry.setLineColor(Color.parseColor("#F9AD19"));
                    avVerify.setVisibility(View.GONE);
                    Snackbar.with(getApplicationContext()) // context
                            .text("Incorrect code, Please try again ") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(Verification.this);


                }
            }
        });


    }


    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }

    public Typeface lightfont()
    {
        Typeface robotolight = Typeface.createFromAsset(getAssets(),  "fonts/roboto.ttf");

        return robotolight;
    }



}

