package com.android.khoog.createevent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class step2 extends AppCompatActivity {
    Button evntBtn, plusbtn;
    CheckBox cb, cb_meal, cb_team, cb_health, cb_trans, cb_others;
    EditText et, et_meal, et_team, et_health, et_trans, et_others;

    CheckBox cb_accex, cb_mealex, cb_teamex, cb_healthex, cb_transex, cb_otherex;
    EditText et_accex, et_mealex, et_teamex, et_healthex, et_transex, et_othersex, et_desp, treksticks;

    String eventid;
    ImageView Back;

    List<EditText> listArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step7);
        cb = (CheckBox)findViewById(R.id.accomodationcheckbox);
        cb.setTypeface(boldfont());
        et = (EditText)findViewById(R.id.accomodationText);
        cb_meal = (CheckBox)findViewById(R.id.meal);
        cb_meal.setTypeface(boldfont());
        et_meal = (EditText)findViewById(R.id.mealText);
        cb_team = (CheckBox) findViewById(R.id.team);
        cb_team.setTypeface(boldfont());
        et_team = (EditText)findViewById(R.id.teamText);
        cb_health = (CheckBox)findViewById(R.id.health);
        cb_health.setTypeface(boldfont());
        et_health = (EditText)findViewById(R.id.healthText);
        cb_trans = (CheckBox)findViewById(R.id.transportation);
        cb_trans.setTypeface(boldfont());
        et_trans = (EditText)findViewById(R.id.transportationText);
        cb_others = (CheckBox)findViewById(R.id.others);
        cb_others.setTypeface(boldfont());
        et_others = (EditText)findViewById(R.id.othersText);



        SharedPreferences sp = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);
        eventid = sp.getString("eventid", "");

        et_desp = (EditText)findViewById(R.id.description);

        treksticks = (EditText)findViewById(R.id.trakkingStick);
        cb_accex = (CheckBox)findViewById(R.id.accomodationExcluded);
        cb_accex.setTypeface(boldfont());
        et_accex = (EditText)findViewById(R.id.accomodationTextExcluded);
        cb_mealex = (CheckBox)findViewById(R.id.mealExcluded);
        cb_mealex.setTypeface(boldfont());
        et_mealex = (EditText)findViewById(R.id.mealTextExcluded);
        cb_teamex = (CheckBox) findViewById(R.id.teamExcluded);
        cb_teamex.setTypeface(boldfont());
        et_teamex = (EditText)findViewById(R.id.teamTextExcluded);
        cb_healthex = (CheckBox)findViewById(R.id.healthExcluded);
        cb_healthex.setTypeface(boldfont());
        et_healthex = (EditText)findViewById(R.id.healthTextExcluded);
        cb_transex = (CheckBox)findViewById(R.id.transportationExcluded);
        cb_transex.setTypeface(boldfont());
        et_transex = (EditText)findViewById(R.id.transportationTextExcluded);
        cb_otherex = (CheckBox)findViewById(R.id.othersExcluded);
        cb_otherex.setTypeface(boldfont());
        et_othersex = (EditText)findViewById(R.id.othersTextExcluded);

        listArray = new ArrayList<>();


        et_meal.setEnabled(false);
        et_team.setEnabled(false);
        et_health.setEnabled(false);
        et_trans.setEnabled(false);
        et_others.setEnabled(false);
        et.setEnabled(false);


        et_mealex.setEnabled(false);
        et_teamex.setEnabled(false);
        et_healthex.setEnabled(false);
        et_transex.setEnabled(false);
        et_othersex.setEnabled(false);
        et_accex.setEnabled(false);

        cb_others.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_others.isChecked())
                {
                    et_others.setEnabled(true);
                    et_others.requestFocus();
                }else{
                    et_others.setEnabled(false);
                }
            }
        });


        cb_trans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_trans.isChecked())
                {
                    et_trans.setEnabled(true);
                    et_trans.requestFocus();
                }else{
                    et_trans.setEnabled(false);
                }
            }
        });



        cb_health.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_health.isChecked())
                {
                    et_health.setEnabled(true);
                    et_health.requestFocus();
                }else{
                    et_health.setEnabled(false);
                }
            }
        });

        cb_team.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_team.isChecked())
                {
                    et_team.setEnabled(true);
                    et_team.requestFocus();
                }else{
                    et_team.setEnabled(false);
                }
            }
        });





        cb_meal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_meal.isChecked())
                {
                    et_meal.setEnabled(true);
                    et_meal.requestFocus();
                }else{
                    et_meal.setEnabled(false);
                }
            }
        });


        Back=(ImageView)findViewById(R.id.backarrow);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(step2.this,step1.class);
                startActivity(intent);
                finish();
            }
        });


        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb.isChecked())
                {
                    et.setEnabled(true);
                    et.requestFocus();
                }else{
                    et.setEnabled(false);
                }
            }
        });









        // Exlcuded Code Starts Here


        cb_accex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_accex.isChecked())
                {
                    et_accex.setEnabled(true);
                    et_accex.requestFocus();
                }else{
                    et_accex.setEnabled(false);
                }
            }
        });


        cb_otherex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_otherex.isChecked())
                {
                    et_othersex.setEnabled(true);
                    et_othersex.requestFocus();
                }else{
                    et_othersex.setEnabled(false);
                }
            }
        });


        cb_transex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_transex.isChecked())
                {
                    et_transex.setEnabled(true);
                    et_transex.requestFocus();
                }else{
                    et_transex.setEnabled(false);
                }
            }
        });



        cb_healthex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_healthex.isChecked())
                {
                    et_healthex.setEnabled(true);
                    et_healthex.requestFocus();
                }else{
                    et_healthex.setEnabled(false);
                }
            }
        });

        cb_teamex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_teamex.isChecked())
                {
                    et_teamex.setEnabled(true);
                    et_teamex.requestFocus();
                }else{
                    et_teamex.setEnabled(false);
                }
            }
        });





        cb_mealex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_mealex.isChecked())
                {
                    et_mealex.setEnabled(true);
                    et_mealex.requestFocus();
                }else{
                    et_mealex.setEnabled(false);
                }
            }
        });


        // Excluded Code Ends Here









        plusbtn = (Button)findViewById(R.id.carry_hide);
        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout linLayout= new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linLayout.setOrientation(LinearLayout.HORIZONTAL);
                final float scale = step2.this.getResources().getDisplayMetrics().density;
                int pixels = (int) (50 * scale + 0.5f);

                int et_width = (int)(250*scale + 0.5f);


                LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(et_width,pixels);
                EditText edit1 = new EditText(getApplicationContext());
                edit1.setHint("Living Room");
                edit1.setTextSize(12);
                edit1.setHintTextColor(getResources().getColor(R.color.grey));
                edit1.setTextColor(getResources().getColor(R.color.black));
                edit1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rounded_edittext));
                edit1.setId(R.id.my_button_1);
                lpView.setMargins(0,10,0,5);
                edit1.setLayoutParams(lpView);

                LinearLayout.LayoutParams lpView1 = new LinearLayout.LayoutParams(pixels, pixels);
                Button btnHide = new Button(getApplicationContext());
                btnHide.setText("-");
                btnHide.setTextSize(20);

                btnHide.setTextColor(getResources().getColor(R.color.white));
                btnHide.setBackground(ResourcesCompat.getDrawable(getResources(),(R.drawable.hide_background),null));
                lpView1.setMargins(40,20,0,0);
                btnHide.setLayoutParams(lpView1);

                linLayoutParam.setMargins(0,20,0,0);
                linLayout.setLayoutParams(linLayoutParam);

                linLayout.addView(edit1);
                linLayout.addView(btnHide);
                final LinearLayout test = (LinearLayout)findViewById(R.id.plusButton);
                test.addView(linLayout);

                listArray.add(edit1);

                btnHide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        test.removeView(linLayout);
                    }
                });
            }
        });

        evntBtn = (Button)findViewById(R.id.EventBtn1);

        evntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pgDialog;
                pgDialog = new ProgressDialog(step2.this);
                pgDialog.setMessage("Please wait...");
                pgDialog.show();
                pgDialog.setCanceledOnTouchOutside(false);
                pgDialog.show();


                String stacc, stmeal, stteam, sthealth, sttrans, stothers, staccex, stmealex, sthealthex, stothersex, stteamex, sttransex;

                if (cb.isChecked())
                {
                    stacc = et.getText().toString();
                }else{
                    stacc = "";
                }

                if (cb_accex.isChecked())
                {
                    staccex = et_accex.getText().toString();
                }else{
                    staccex = "";
                }


                if (cb_meal.isChecked())
                {
                    stmeal = et_meal.getText().toString();
                }else{
                    stmeal = "";
                }

                if (cb_mealex.isChecked())
                {
                    stmealex = et_mealex.getText().toString();
                }else{
                    stmealex = "";
                }

                if (cb_team.isChecked())
                {
                    stteam = et_team.getText().toString();
                }else{
                    stteam = "";
                }

                if (cb_teamex.isChecked())
                {
                    stteamex = et_teamex.getText().toString();
                }else{
                    stteamex = "";
                }

                if (cb_trans.isChecked())
                {
                    sttrans = et_trans.getText().toString();
                }else{
                    sttrans = "";
                }

                if (cb_transex.isChecked())
                {
                    sttransex = et_transex.getText().toString();
                }else{
                    sttransex = "";
                }

                if (cb_others.isChecked())
                {
                    stothers = et_others.getText().toString();
                }else{
                    stothers = "";
                }

                if (cb_otherex.isChecked())
                {
                    stothersex = et_othersex.getText().toString();
                }else{
                    stothersex = "";
                }

                if (cb_health.isChecked())
                {
                    sthealth = et_health.getText().toString();
                }else{
                    sthealth = "";
                }

                if (cb_healthex.isChecked())
                {
                    sthealthex = et_healthex.getText().toString();
                }else{
                    sthealthex = "";
                }




                String desps = et_desp.getText().toString();
                Constants cnts = new Constants();

                JSONArray jsonArray = new JSONArray();

                String stTrek = treksticks.getText().toString();
                int count = 0;
                if (listArray.size() > 0)
                {
                    while (count < listArray.size())
                    {
                        String gettexts = listArray.get(count).getText().toString();
                        jsonArray.put(gettexts);
                        count++;
                    }
                }

                jsonArray.put(stTrek);

                String url = cnts.getUrl();
                AndroidNetworking.post(url)
                        .addBodyParameter("action", "eventServices")
                        .addBodyParameter("AccomodationInput", stacc)
                        .addBodyParameter("MealInput", stmeal)
                        .addBodyParameter("TeamInput", stteam)
                        .addBodyParameter("HealthInput", sthealth)
                        .addBodyParameter("TransportationInput", sttrans)
                        .addBodyParameter("OthersInput", stothers)
                        .addBodyParameter("eventExcluded2", staccex)
                        .addBodyParameter("eventExcluded4", stmealex)
                        .addBodyParameter("eventExcluded6", stteamex)
                        .addBodyParameter("eventExcluded8", sthealthex)
                        .addBodyParameter("eventExcluded10", sttransex)
                        .addBodyParameter("eventExcluded12", stothersex)
                        .addBodyParameter("specialNotes", desps)
                        .addBodyParameter("device", "android")
                        .addBodyParameter("carryOn[]", jsonArray.toString())
                        .addBodyParameter("hashId", eventid)
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

                                        if (pgDialog.isShowing()){
                                            pgDialog.dismiss();
                                        }


                                        Intent intent = new Intent(step2.this, step3.class);
                                        startActivity(intent);
                                        finish();



                                    }else{

                                        if (pgDialog.isShowing()){
                                            pgDialog.dismiss();
                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Toast.makeText(step2.this, "Some error occured", Toast.LENGTH_SHORT).show();


                                if (pgDialog.isShowing()){
                                    pgDialog.dismiss();
                                }
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
