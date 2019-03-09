package com.android.khoog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.nispok.snackbar.Snackbar;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.layout1) LinearLayout layout_mail;
    @BindView(R.id.email) EditText et_email;
    @BindView(R.id.password)EditText et_pass;
    @BindView(R.id.layout2) LinearLayout layout_pass;
    @BindView(R.id.login) Button loginbtn;
    @BindView(R.id.reg) TextView tv_reg;
    @BindView(R.id.partner)TextView partnerText;
    @BindView(R.id.registerUperText) TextView uppertext;
    @BindView(R.id.becomePartner) TextView bptext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        et_email.setTypeface(boldfont());
        et_pass.setTypeface(boldfont());
        loginbtn.setTypeface(boldfont());
        tv_reg.setTypeface(boldfont());
        partnerText.setTypeface(boldfont());
        uppertext.setTypeface(boldfont());
        bptext.setTypeface(boldfont());
        tv_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(LoginActivity.this, RegisterUser.class);
                startActivity(in);
                finish();
            }
        });

        // Become a partner button
        partnerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(LoginActivity.this, GetStarted.class);
                startActivity(in);
                finish();

            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!et_email.getText().toString().isEmpty()){

                    layout_mail.setBackgroundResource(R.drawable.normal_background);
                }

                if (!et_pass.getText().toString().isEmpty())
                {
                    layout_pass.setBackgroundResource(R.drawable.normal_background);
                }

                if(et_email.getText().toString().isEmpty()){
                    changeBackgroundOfLinear(layout_mail);
                    showTastyToast("Please enter a E-Mail Address!");

                }else if (!Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()){
                    changeBackgroundOfLinear(layout_mail);
                    showTastyToast("Please enter a Valid E-Mail Address!");

                }

                else if (et_pass.getText().toString().isEmpty()){
                    changeBackgroundOfLinear(layout_pass);
                    showTastyToast("Please enter a correct password");

                }
                else {

                    final ProgressDialog pgDialog;
                    pgDialog = new ProgressDialog(LoginActivity.this);
                    pgDialog.setMessage("Please wait...");
                    pgDialog.show();
                    pgDialog.setCanceledOnTouchOutside(false);

                    String email = et_email.getText().toString();

                    String password = et_pass.getText().toString();

                    Constants constants = new Constants();
                    AndroidNetworking.post(constants.getUrl())
                            .addBodyParameter("action", constants.getloginaction())
                            .addBodyParameter("loginEmail", email)
                            .addBodyParameter("loginPassword", password)
                            .setTag("test")
                            .setPriority(Priority.LOW)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // do anything with response+
                                    if (pgDialog.isShowing())
                                    {
                                        pgDialog.dismiss();
                                    }
                                    try {

                                        String result = response.getString("result");

                                        if (result.equals("email"))
                                        {

                                            Snackbar.with(getApplicationContext()) // context
                                                    .text("Your Email is incorrect") // text to display
                                                    .color(Color.RED)
                                                    .textColor(Color.WHITE)
                                                    .show(LoginActivity.this);

                                        }else if (result.equals("password"))
                                        {


                                            Snackbar.with(getApplicationContext()) // context
                                                    .text("Your Password is incorrect") // text to display
                                                    .color(Color.RED)
                                                    .textColor(Color.WHITE)
                                                    .show(LoginActivity.this);

                                        }else{

                                            String userid = response.getString("user_id");
                                            String usertype = response.getString("ustype");
                                            SharedPreferences sharedPreferences = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor= sharedPreferences.edit();
                                            editor.putString("userid", userid);
                                            editor.putString("usertype", usertype);
                                            editor.apply();

                                            if (usertype.equals("user"))
                                            {

                                                Intent in = new Intent(LoginActivity.this, Homepage.class);
                                                startActivity(in);
                                                finish();

                                            }else{



                                                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                                startActivity(intent);
                                                finish();



                                            }



                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error

                                    if (pgDialog.isShowing())
                                    {
                                        pgDialog.dismiss();
                                    }


                                    Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_LONG).show();

                                }
                            });

                }
            }
        });
    }


    public void showTastyToast(String showtext)
    {
        TastyToast.makeText(getApplicationContext(), showtext, TastyToast.LENGTH_LONG, TastyToast.ERROR);
    }

    public void changeBackgroundOfLinear(LinearLayout linearLayout)
    {

        linearLayout.setBackgroundResource(R.drawable.error_background);
        linearLayout.requestFocus();
    }



    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }

}
