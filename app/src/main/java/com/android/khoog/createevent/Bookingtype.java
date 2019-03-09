package com.android.khoog.createevent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.R;
import com.android.khoog.SuccessfullyCreate;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Bookingtype extends AppCompatActivity {

    RadioButton rb_auto, rb_pre;
    Button finishEvent;
    TextView t1, t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingtype);

        rb_auto = (RadioButton)findViewById(R.id.rb_auto);
        rb_pre = (RadioButton)findViewById(R.id.rb_pre);

        rb_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rb_auto.isChecked())
                {

                    t2.setVisibility(View.VISIBLE);
                    t1.setVisibility(View.GONE);
                }
            }
        });

        rb_pre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rb_auto.isChecked())
                {
                    t1.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.GONE);
                }
            }
        });

        t1 = (TextView)findViewById(R.id.preText);
        t2 = (TextView)findViewById(R.id.autoText);

        finishEvent = (Button)findViewById(R.id.wpBtn);
        finishEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getbookingtype = "";
                if (rb_auto.isChecked())
                {
                    getbookingtype = "2";
                }

                if (rb_pre.isChecked())
                {
                    getbookingtype = "1";

                }


                SharedPreferences sp = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);
                final String eventid = sp.getString("eventid","");

                final ProgressDialog pgDialog;
                pgDialog = new ProgressDialog(Bookingtype.this);
                pgDialog.setMessage("Please wait...");
                pgDialog.show();
                pgDialog.setCanceledOnTouchOutside(false);
                pgDialog.show();


                Constants cnts = new Constants();
                String url = cnts.getUrl();
                AndroidNetworking.post(url)
                        .addBodyParameter("action", "bookingtypeinsert")
                        .addBodyParameter("eventid", eventid)
                        .addBodyParameter("type", getbookingtype)
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

                                        pgDialog.dismiss();
                                        Intent intent = new Intent(Bookingtype.this, SuccessfullyCreate.class);
                                        intent.putExtra("eventid",eventid);
                                        startActivity(intent);

                                        finish();


                                    }else{
                                        pgDialog.dismiss();
                                        Toast.makeText(Bookingtype.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    pgDialog.dismiss();
                                }

                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                pgDialog.dismiss();
                                Toast.makeText(Bookingtype.this, "Some error occured", Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });


    }
}
