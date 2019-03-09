package com.android.khoog.createevent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.Constants.SpinnerAdapter;
import com.android.khoog.Constants.SpinnerArrayAdapter;
import com.android.khoog.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class step4 extends AppCompatActivity  {
    Button discountBtn;
    String grups[] = {"5%","10%","15%","20%","25%","30%","35%","40%","45%","50%","55%","60%","Free"};
    String dis[] = {"10%","15%","20%","25%","30%","35%","40%","45%","50%","55%","60%"};
    String kids[] = {"Free","2%","3%","4%","5%","10%","15%","20%","25%","30%","35%","40%","45%","50%","55%","60%"};
    String persn[] = {"5 Person","10 Person","15 Person","20 Person"};
    String age[] = {"5 Years","10 Years","15 Years","20 Years"};
    String mF[] = {"Woman","Man"};


    String rfdays, rfpercentage;


    String StrictPercentage[] = {"0%","5%","10%","15%","20%","25%"};
    String ModeratePercentage[] = {"30%","35%","40%","45%","50%","55%","60%","65%","70%","75%"};
    String FlexiblePercentage[] = {"75%","80%","85%","90%","95%","100%"};


    String refundmethod = "Flexible";

    ArrayList<String> daysArray = new ArrayList<>();

    CheckBox groupCheckBox;

    String groupDiscountPercentage, getGroupDiscountPerson, kidsPercentage, kidsAge, SinglePercentage, SingleGender, couplePercentage, studentPercentage;

    ImageView backarrow3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step9);

        final RadioButton mdtbox, flexbox, strictbox;

        mdtbox = (RadioButton)findViewById(R.id.moderate_box);
        flexbox = (RadioButton)findViewById(R.id.flexible_box);
        strictbox = (RadioButton)findViewById(R.id.strict_box);

        final SpinnerAdapter modertateArray = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item, ModeratePercentage);
        final SpinnerAdapter strictArray = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item, StrictPercentage);



        groupCheckBox = (CheckBox)findViewById(R.id.groupCheckBox);
        groupCheckBox.setTypeface(boldfont());

        SharedPreferences spevent = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);
        String date1 = spevent.getString("eventstartingdate","");
        long parseDate = Date.parse(date1);
        Date getNowDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("EEE dd MMM yyyy");
        String formattedDate = df.format(getNowDate);
        long longNowDate = Date.parse(formattedDate);
        long Difference = parseDate - longNowDate;
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = Difference/ daysInMilli;
        Integer getdays = (int)(long) elapsedDays;

        for (int i = 2; i <= getdays ; i++)
        {

            String dayComplete = String.valueOf(i);
            String dayShow = dayComplete+" Days";
            daysArray.add(dayShow);

        }



        Spinner refundDaysSpinner = (Spinner)findViewById(R.id.refundCancelSpin);
        final SpinnerArrayAdapter rDaysSpinner = new SpinnerArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, daysArray);
        refundDaysSpinner.setAdapter(rDaysSpinner);

        final Spinner refundSpinner = (Spinner)findViewById(R.id.eligibleSpin);
        final SpinnerAdapter flexibleArray = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item, FlexiblePercentage);
        refundSpinner.setAdapter(flexibleArray);


        refundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (flexbox.isChecked())
                {
                    rfpercentage = flexibleArray.getItem(i).toString();
                }

                if (strictbox.isChecked())
                {
                    rfpercentage = strictArray.getItem(i).toString();

                }

                if (mdtbox.isChecked())
                {
                    rfpercentage = modertateArray.getItem(i).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        refundDaysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rfdays = rDaysSpinner.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        mdtbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (mdtbox.isChecked())
                {
                    refundSpinner.setAdapter(modertateArray);
                    refundmethod = "Moderate";
                }
            }
        });

        flexbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (flexbox.isChecked())
                {
                    refundSpinner.setAdapter(flexibleArray);
                    refundmethod = "Flexible";
                }
            }
        });

        strictbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (strictbox.isChecked())
                {
                    refundSpinner.setAdapter(strictArray);
                    refundmethod = "Strict";
                }
            }
        });

        // Group Spin Percnetage
        Spinner spinner_grp = (Spinner)findViewById(R.id.groupSpin);
        final SpinnerAdapter grp_arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,grups);
        spinner_grp.setAdapter(grp_arrayAdapter);


        // Group Spin Percentage Functionality

        spinner_grp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                groupDiscountPercentage = grp_arrayAdapter.getItem(i).toString();
                groupDiscountPercentage = groupDiscountPercentage.replace("%","");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Spinner spinner_every = (Spinner)findViewById(R.id.onEverySpin);
        final SpinnerAdapter every_arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,persn);
        spinner_every.setAdapter(every_arrayAdapter);



        spinner_every.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getGroupDiscountPerson = every_arrayAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        Spinner spinner_age = (Spinner)findViewById(R.id.onAgeSpin);
        final SpinnerAdapter age_arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,age);
        spinner_age.setAdapter(age_arrayAdapter);

        Spinner spinner_kids = (Spinner)findViewById(R.id.discountKidSpin);
        final SpinnerAdapter kids_arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,kids);
        spinner_kids.setAdapter(kids_arrayAdapter);


        spinner_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kidsAge = age_arrayAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_kids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kidsPercentage = kids_arrayAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });







        Spinner spinner_single = (Spinner)findViewById(R.id.singleSpin);
        final SpinnerAdapter single_arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,dis);
        spinner_single.setAdapter(single_arrayAdapter);

        Spinner spinner_for = (Spinner)findViewById(R.id.forSpin);
        final SpinnerAdapter for_arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,mF);
        spinner_for.setAdapter(for_arrayAdapter);


        spinner_single.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SinglePercentage = single_arrayAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_for.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SingleGender = for_arrayAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        Spinner spinner_couple = (Spinner)findViewById(R.id.coupleSpin);
        final SpinnerAdapter couple_arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,dis);
        spinner_couple.setAdapter(couple_arrayAdapter);


        spinner_couple.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                couplePercentage = couple_arrayAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinner_students = (Spinner)findViewById(R.id.studentSpin);
        final SpinnerAdapter student_arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,dis);
        spinner_students.setAdapter(student_arrayAdapter);

        spinner_students.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                studentPercentage = student_arrayAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        backarrow3 = (ImageView)findViewById(R.id.backarrowstep4);
        backarrow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(step4.this, step3.class);
                startActivity(intent);
                finish();
            }
        });

        final CheckBox kidsCheckbox = (CheckBox)findViewById(R.id.discountKid_box);
        kidsCheckbox.setTypeface(boldfont());

        final CheckBox singleCheckbox = (CheckBox)findViewById(R.id.single_box);
        singleCheckbox.setTypeface(boldfont());

        final CheckBox coupleCheckbox = (CheckBox)findViewById(R.id.couple_box);
        coupleCheckbox.setTypeface(boldfont());


        final CheckBox studentCheckbox = (CheckBox)findViewById(R.id.student_box);
        studentCheckbox.setTypeface(boldfont());

        discountBtn = (Button) findViewById(R.id.discountEventBtn);
        discountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final ProgressDialog pgDialog;
                pgDialog = new ProgressDialog(step4.this);
                pgDialog.setMessage("Please wait...");
                pgDialog.show();
                pgDialog.setCanceledOnTouchOutside(false);
                pgDialog.show();


                SharedPreferences sharedPreferences = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);
                String eventid = sharedPreferences.getString("eventid","");

                SharedPreferences loginPref = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
                String compid = loginPref.getString("userid","");

                String GroupStatus;
                if (groupCheckBox.isChecked())
                {
                    GroupStatus = "checked";
                }else{
                    GroupStatus = "notchecked";
                }


                String kidsStatus;
                if (kidsCheckbox.isChecked())
                {
                    kidsStatus = "checked";
                }else{
                    kidsStatus = "notchecked";
                }


                String singleCheck;
                if (singleCheckbox.isChecked())
                {
                    singleCheck = "checked";
                }else{
                    singleCheck = "notchecked";
                }




                String coupleCheck;

                if (coupleCheckbox.isChecked())
                {
                    coupleCheck = "checked";
                }else{
                    coupleCheck = "notchecked";
                }

                String studentCheck;

                if (studentCheckbox.isChecked())
                {
                    studentCheck = "checked";
                }else{
                    studentCheck = "notchecked";
                }

                Constants cnts = new Constants();
                String url = cnts.getUrl();
                AndroidNetworking.post(url)
                        .addBodyParameter("action", "eventServices")
                        .addBodyParameter("event_id", eventid)
                        .addBodyParameter("compid", compid)
                        .addBodyParameter("Group",GroupStatus)
                        .addBodyParameter("action","refunds")
                        .addBodyParameter("discountGroup",groupDiscountPercentage)
                        .addBodyParameter("groupperson", getGroupDiscountPerson)
                        .addBodyParameter("discountsKids", kidsPercentage)
                        .addBodyParameter("kidsAge", kidsAge)
                        .addBodyParameter("Kids",kidsStatus)
                        .addBodyParameter("Singles",singleCheck)
                        .addBodyParameter("discountSingles", SinglePercentage)
                        .addBodyParameter("singlesFor", SingleGender)
                        .addBodyParameter("device","android")
                        .addBodyParameter("Couples",coupleCheck)
                        .addBodyParameter("Students",studentCheck)
                        .addBodyParameter("discountCouples",couplePercentage)
                        .addBodyParameter("discountStudents",studentPercentage)
                        .addBodyParameter("refundPercentage",rfpercentage)
                        .addBodyParameter("refundDays",rfdays)
                        .addBodyParameter("refund",refundmethod)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // do anything with response
                                Toast.makeText(step4.this, response.toString(), Toast.LENGTH_SHORT).show();
                                try {
                                    String result = response.getString("result");
                                    if (result.equals("success"))
                                    {

                                        if (pgDialog.isShowing())
                                        {
                                            pgDialog.dismiss();
                                        }
                                        Intent intent = new Intent(step4.this, step5.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        pgDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    pgDialog.dismiss();
                                    e.printStackTrace();
                                }

                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Toast.makeText(step4.this, "Some error occured", Toast.LENGTH_SHORT).show();
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
