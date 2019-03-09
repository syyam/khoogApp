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

public class addtransport extends AppCompatActivity {

    RadioButton rbArrival, rbDept;

    EditText where, time, notes;

    int mHour, mMin;

    String act;

    Button adTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtransport);

        ImageView imgView = (ImageView)findViewById(R.id.backarrow);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addtransport.this, step5.class);
                startActivity(intent);
                finish();
            }
        });

        adTrans = (Button)findViewById(R.id.adtransporsystem);

        Intent intent = getIntent();
        final String getday = intent.getStringExtra("day");

        SharedPreferences sp = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);
        final String eventid = sp.getString("eventid", "");

        SharedPreferences splogin = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        final String userid = splogin.getString("userid","");

        rbArrival = (RadioButton)findViewById(R.id.rb_arrival);
        rbDept = (RadioButton)findViewById(R.id.rb_departure);





        where = (EditText)findViewById(R.id.where);
        time = (EditText)findViewById(R.id.time);
        notes = (EditText)findViewById(R.id.notes);



        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog tmdialog;
                tmdialog = new TimePickerDialog(addtransport.this, new TimePickerDialog.OnTimeSetListener() {
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

                        time.setText(exactTime);

                    }
                }, mHour, mMin, false);
                tmdialog.show();
            }
        });


        adTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rbArrival.isChecked())
                {
                    act = "Arrival";
                }

                if (rbDept.isChecked())
                {
                    act = "Departure";
                }

                final ProgressDialog pgDialog;
                pgDialog = new ProgressDialog(addtransport.this);
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
                        .addBodyParameter("title","")
                        .addBodyParameter("where",where.getText().toString())
                        .addBodyParameter("time", time.getText().toString())
                        .addBodyParameter("notes",notes.getText().toString())
                        .addBodyParameter("type", "transport")
                        .addBodyParameter("act", act)
                        .addBodyParameter("dayitt2", getday)
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
                                        Intent intent = new Intent(addtransport.this, step5.class);
                                        intent.putExtra("dayss",getday);
                                        startActivity(intent);
                                        finish();

                                    }else{
                                        pgDialog.dismiss();
                                        Toast.makeText(addtransport.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(addtransport.this, "Some error occured", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
















    }
}
