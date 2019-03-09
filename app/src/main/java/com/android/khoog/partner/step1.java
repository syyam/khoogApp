package com.android.khoog.partner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.khoog.Constants.Constants;
import com.android.khoog.GetStarted;
import com.android.khoog.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.nispok.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class step1 extends AppCompatActivity {
    ImageView back;
    Button btn;
    LinearLayout l1,l2;
    String type = "comp", userid, useremail;
    SharedPreferences sharedPreferences;
    @BindView(R.id.welcome)
    TextView welcome;
    @BindView(R.id.howOperate)
    TextView howOperate;
    @BindView(R.id.getStarted)
    TextView getStarted;
    @BindView(R.id.companyFont)
    TextView compfont;
    @BindView(R.id.indifont)
    TextView indifont;

    ProgressDialog pgDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);
        ButterKnife.bind(this);
        btn = (Button) findViewById(R.id.wpBtn);
        back = (ImageView) findViewById(R.id.backarrow);
        welcome.setTypeface(boldfont());
        getStarted.setTypeface(boldfont());
        howOperate.setTypeface(boldfont());
        compfont.setTypeface(boldfont());
        indifont.setTypeface(boldfont());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(step1.this, GetStarted.class);
                startActivity(intent);
                finish();
            }
        });


        SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        userid = sp.getString("userid","");
        useremail = sp.getString("useremail", "");
        String fname = sp.getString("fn","");
        String lname = sp.getString("ln","");

        welcome.setText("Wecome "+fname+" "+lname);


        l1 = (LinearLayout) findViewById(R.id.layout1);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setBackgroundResource(R.drawable.select_border);
                l2.setBackgroundResource(R.drawable.border_draw);
                type = "comp";
            }
        });


        l2 = (LinearLayout) findViewById(R.id.layout2);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setBackgroundResource(R.drawable.border_draw);
                l2.setBackgroundResource(R.drawable.select_border);
                type = "indi";
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants constants = new Constants();


                pgDialog = new ProgressDialog(step1.this);
                pgDialog.setMessage("Please wait...");
                pgDialog.show();
                pgDialog.setCanceledOnTouchOutside(false);


                AndroidNetworking.post(constants.getUrl())
                        .addBodyParameter("action", "companytype")
                        .addBodyParameter("compid", userid)
                        .addBodyParameter("email", useremail)
                        .addBodyParameter("companytype", type)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                try {

                                    String result = response.getString("result");

                                    if (result.equals("success"))
                                    {

                                        if (pgDialog.isShowing())
                                        {
                                            pgDialog.dismiss();
                                        }

                                        sharedPreferences = getSharedPreferences("userinfo",Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor= sharedPreferences.edit();
                                        editor.putString("type",type);
                                        editor.apply();
                                        Intent intent = new Intent(step1.this, step2.class);
                                        startActivity(intent);
                                        finish();
                                    }else{

                                        if (pgDialog.isShowing())
                                        {
                                            pgDialog.dismiss();
                                        }


                                        Snackbar.with(getApplicationContext()) // context
                                                .text("Some error occred ") // text to display
                                                .color(Color.RED)
                                                .textColor(Color.WHITE)
                                                .show(step1.this);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                            }
                        });


            }
        });

    }

    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }

}
