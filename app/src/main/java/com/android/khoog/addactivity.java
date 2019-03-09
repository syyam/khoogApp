package com.android.khoog;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.createevent.step5;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class addactivity extends AppCompatActivity {

    EditText tit, whe, timeee, notesss;

    int mHour, mMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);

        ImageView imgView = (ImageView)findViewById(R.id.backarrow);

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addactivity.this, step5.class);
                startActivity(intent);
                finish();
            }
        });



        Intent intent = getIntent();
        final String getday = intent.getStringExtra("day");

        SharedPreferences sp = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);
        final String eventid = sp.getString("eventid", "");

        SharedPreferences splogin = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        final String userid = splogin.getString("userid","");

        Button saveAct = (Button)findViewById(R.id.saveActivity);

        final RadioButton rbFood, rbActivity;

        rbFood = (RadioButton)findViewById(R.id.rb_foods);
        rbActivity = (RadioButton)findViewById(R.id.rb_activity);

        tit = (EditText)findViewById(R.id.activitytitle);
        whe = (EditText)findViewById(R.id.where);
        timeee = (EditText)findViewById(R.id.time);
        notesss = (EditText)findViewById(R.id.notes);


        timeee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog tmdialog;
                tmdialog = new TimePickerDialog(addactivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        String hour = String.valueOf(i);
                        String minute = String.valueOf(i1);

                        if (minute.equals("0"))
                        {
                            minute = "0"+minute;
                        }
                        String AmPm = "";
                        if (i < 12)
                        {
                            AmPm = "AM";
                        }else{
                            AmPm = "PM";
                        }

                        String exactTime = hour + ":"+minute+" "+AmPm;

                        timeee.setText(exactTime);

                    }
                }, mHour, mMin, false);
                tmdialog.show();
            }
        });


        saveAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String activitiyCheck = "";


                if (rbFood.isChecked())
                {
                    activitiyCheck = "Foods";
                }

                if (rbActivity.isChecked())
                {
                    activitiyCheck = "Activity";
                }

                final ProgressDialog pgDialog;
                pgDialog = new ProgressDialog(addactivity.this);
                pgDialog.setMessage("Please wait...");
                pgDialog.show();
                pgDialog.setCanceledOnTouchOutside(false);
                pgDialog.show();



                Constants cnts = new Constants();
                String url = cnts.getUrl();
                AndroidNetworking.post(url)
                        .addBodyParameter("action", "itt")
                        .addBodyParameter("khoog_id", userid)
                        .addBodyParameter("id", eventid)
                        .addBodyParameter("title", tit.getText().toString())
                        .addBodyParameter("where", whe.getText().toString())
                        .addBodyParameter("time", timeee.getText().toString())
                        .addBodyParameter("notes", notesss.getText().toString())
                        .addBodyParameter("type", "activity")
                        .addBodyParameter("act", activitiyCheck)
                        .addBodyParameter("dayitt", getday)
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
                                        String getr = response.getString("datar");
                                        Intent intent = new Intent(addactivity.this, step5.class);
                                        intent.putExtra("dayss",getday);
                                        startActivity(intent);
                                        finish();

                                    }else{
                                        pgDialog.dismiss();
                                        Toast.makeText(addactivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    pgDialog.dismiss();
                                    e.printStackTrace();
                                }

                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                pgDialog.dismiss();
                                Toast.makeText(addactivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                            }
                        });




            }
        });
    }
}
