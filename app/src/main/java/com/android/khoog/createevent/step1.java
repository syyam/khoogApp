package com.android.khoog.createevent;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.Constants.SpinnerAdapter;
import com.android.khoog.Dashboard;
import com.android.khoog.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.nispok.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class step1 extends AppCompatActivity {

    String sp[]={"Punjab","Sindh","Balochistan","Khyber Pakhtunkhwa","Gilgit-Baltistan"};
    ImageView bckArro;
    EditText et_title,et_loc,et_option,et_topAt,et_discrp, et_spots;
    TextView tv_startDate,tv_endDate, tv_act;
    LinearLayout l1,l2,l3;
    Button Next_btn,adMore;
    String provices, getuserid;
    List<EditText> etArray;
    CheckBox his, edu, lei, fam, cul, corp, adv, pri, ss, bf, hiking, sb, ds, photo, treking, sking, fly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step6);
        initiate();

        SharedPreferences sharedpref = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        getuserid = sharedpref.getString("userid", "");


        etArray = new ArrayList<>();

        et_spots = (EditText)findViewById(R.id.optional);

        his = (CheckBox)findViewById(R.id.historical);
        edu = (CheckBox)findViewById(R.id.educational);
        lei = (CheckBox)findViewById(R.id.leisure);
        fam = (CheckBox)findViewById(R.id.family);
        cul = (CheckBox)findViewById(R.id.cultural);
        corp = (CheckBox)findViewById(R.id.corporative);
        adv = (CheckBox)findViewById(R.id.adventure);
        pri = (CheckBox)findViewById(R.id.privat);

        his.setTypeface(boldfont());
        edu.setTypeface(boldfont());
        lei.setTypeface(boldfont());
        fam.setTypeface(boldfont());
        cul.setTypeface(boldfont());
        corp.setTypeface(boldfont());
        adv.setTypeface(boldfont());
        pri.setTypeface(boldfont());

        ss = (CheckBox)findViewById(R.id.sightseeing);
        bf = (CheckBox)findViewById(R.id.bonefire);
        hiking = (CheckBox)findViewById(R.id.hiking);
        sb = (CheckBox)findViewById(R.id.scubaDiving);
        ds = (CheckBox)findViewById(R.id.desertSafari);
        photo = (CheckBox)findViewById(R.id.photography);
        treking = (CheckBox)findViewById(R.id.trekking);
        sking = (CheckBox)findViewById(R.id.skiing);
        fly = (CheckBox)findViewById(R.id.flying);


        ss.setTypeface(boldfont());
        bf.setTypeface(boldfont());
        hiking.setTypeface(boldfont());
        sb.setTypeface(boldfont());
        ds.setTypeface(boldfont());
        ds.setTypeface(boldfont());
        photo.setTypeface(boldfont());
        treking.setTypeface(boldfont());
        sking.setTypeface(boldfont());
        fly.setTypeface(boldfont());


        adMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout linLayout= new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linLayout.setOrientation(LinearLayout.HORIZONTAL);

                final float scale = step1.this.getResources().getDisplayMetrics().density;
                int pixels = (int) (50 * scale + 0.5f);

                int et_width = (int)(250*scale + 0.5f);

                LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(et_width,pixels);
                EditText edit1 = new EditText(getApplicationContext());
                edit1.setHint("Saif Ul Malook");
                edit1.setTypeface(null, Typeface.BOLD);
                edit1.setTypeface(boldfont());
                edit1.setHintTextColor(getResources().getColor(R.color.grey));
                edit1.setTextColor(getResources().getColor(R.color.black));
                edit1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rounded_edittext));
                edit1.setId(R.id.my_button_1);
                lpView.setMargins(0,10,0,5);
                edit1.setLayoutParams(lpView);
                LinearLayout.LayoutParams lpView1 = new LinearLayout.LayoutParams(pixels, pixels);
                Button btnH = new Button(getApplicationContext());
                btnH.setText("-");
                btnH.setTextSize(20);


                etArray.add(edit1);

                int btnLeftMargin = (int)(18*scale + 0.5f);

                btnH.setTextColor(getResources().getColor(R.color.white));
                btnH.setBackground(ResourcesCompat.getDrawable(getResources(),(R.drawable.hide_background),null));
                lpView1.setMargins(btnLeftMargin,10,0,0);
                btnH.setLayoutParams(lpView1);

                linLayoutParam.setMargins(0,10,0,0);
                linLayout.setLayoutParams(linLayoutParam);

                linLayout.addView(edit1);
                linLayout.addView(btnH);
                final LinearLayout test = (LinearLayout)findViewById(R.id.testlayout);
                test.addView(linLayout);

                btnH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        test.removeView(linLayout);
                    }
                });


            }
        });

        final Spinner spinner = (Spinner) findViewById(R.id.spin);
        final SpinnerAdapter arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,sp);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                provices = arrayAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bckArro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(step1.this,Dashboard.class);
                startActivity(intent);
                finish();
            }
        });


        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "EEE MMM dd yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        tv_startDate.setText(sdf.format(myCalendar.getTime()));
                        tv_startDate.setTextColor(Color.BLACK);

                    }
                };

                new DatePickerDialog(step1.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "EEE MMM dd yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        tv_endDate.setText(sdf.format(myCalendar.getTime()));
                        tv_endDate.setTextColor(Color.BLACK);

                    }
                };

                new DatePickerDialog(step1.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        Next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String top_attraction = et_topAt.getText().toString();
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(top_attraction);

                JSONArray typeArray = new JSONArray();

                JSONArray activitiesArray = new JSONArray();

                Boolean type = false;

                Boolean activities = false;

                if (ss.isChecked())
                {
                    activitiesArray.put("SightSeeing");
                    activities = true;
                }

                if (bf.isChecked())
                {
                    activitiesArray.put("Bonefire");
                    activities = true;
                }

                if (hiking.isChecked())
                {
                    activitiesArray.put("Hiking");
                    activities = true;
                }
                if (sb.isChecked())
                {
                    activitiesArray.put("Scuba Diving");
                    activities = true;
                }

                if (ds.isChecked())
                {
                    activitiesArray.put("Desert Safari");
                    activities = true;
                }

                if (photo.isChecked())
                {
                    activities = true;
                    activitiesArray.put("Photography");
                }

                if (treking.isChecked())
                {
                    activities = true;
                    activitiesArray.put("Trekking");
                }

                if (sking.isChecked())
                {
                    activities = true;
                    activitiesArray.put("Skiing");
                }

                if (fly.isChecked())
                {
                    activities = true;
                    activitiesArray.put("Flying");
                }

                if (his.isChecked())
                {

                    typeArray.put("Historical");
                    type = true;
                }

                if (edu.isChecked())
                {
                    typeArray.put("Educational");
                    type = true;
                }

                if (lei.isChecked())
                {
                    typeArray.put("Leisure");
                    type = true;
                }

                if (fam.isChecked())
                {
                    typeArray.put("Family");
                    type = true;
                }


                if (cul.isChecked())
                {
                    typeArray.put("Cultural");
                    type = true;
                }


                if (corp.isChecked())
                {
                    typeArray.put("Corporative");
                    type = true;
                }

                if (pri.isChecked())
                {
                    typeArray.put("Private");
                    type = true;
                }

                int count = 0;

                if (etArray.size() > 0)
                {
                    while (count < etArray.size())
                    {
                        String getEditText = etArray.get(count).getText().toString();
                        jsonArray.put(getEditText);
                        count++;

                    }
                }

                String passingarray = jsonArray.toString();

                final TextView tv_type = (TextView)findViewById(R.id.changeColorType);




                if (!et_title.getText().toString().isEmpty()) {

                    et_title.setBackgroundResource(R.drawable.rounded_edittext);

                }
                if (!et_loc.getText().toString().isEmpty()) {

                    et_loc.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (!tv_startDate.getText().toString().isEmpty()) {

                    l1.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (!tv_endDate.getText().toString().isEmpty()) {

                    l2.setBackgroundResource(R.drawable.rounded_edittext);
                }

                if (!et_option.getText().toString().isEmpty()) {

                    et_option.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (!et_topAt.getText().toString().isEmpty()) {

                    et_topAt.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (!et_discrp.getText().toString().isEmpty()) {

                    et_discrp.setBackgroundResource(R.drawable.rounded_edittext);
                }

                if (type == true)
                {
                    tv_type.setTextColor(Color.BLACK);
                }

                if (activities == true)
                {
                    tv_act.setTextColor(Color.BLACK);
                }

                if (et_title.getText().toString().isEmpty()) {
                    et_title.setBackgroundResource(R.drawable.error_edittext);
                    et_title.requestFocus();

                    Snackbar.with(getApplicationContext()) // context
                            .text("Please Enter Title") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step1.this);

                } else if (et_loc.getText().toString().isEmpty()) {
                    et_loc.setBackgroundResource(R.drawable.error_edittext);
                    et_loc.requestFocus();

                    Snackbar.with(getApplicationContext()) // context
                            .text("Please Enter Location") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step1.this);


                } else if (tv_startDate.getText().toString().isEmpty()) {
                    l1.setBackgroundResource(R.drawable.error_edittext);
                    l1.requestFocus();
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please Enter Start Date") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step1.this);

                } else if (tv_endDate.getText().toString().isEmpty()) {
                    l2.setBackgroundResource(R.drawable.error_edittext);
                    l2.requestFocus();
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please Enter End Date") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step1.this);



                }else if (et_topAt.getText().toString().isEmpty()) {
                    et_topAt.setBackgroundResource(R.drawable.error_edittext);
                    et_topAt.requestFocus();
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please enter at least one top attraction") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step1.this);
                }else if (type == false)
                {
                    tv_type.setTextColor(Color.RED);
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please Select At Least One Type For Your Event") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step1.this);

                }else if (type == false)
                {

                    tv_type.setTextColor(Color.RED);
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please Select At Least One Activity For Your Event") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step1.this);
                }else if (et_discrp.getText().toString().isEmpty())
                {
                    et_discrp.setBackgroundResource(R.drawable.error_edittext);
                    et_discrp.requestFocus();
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please enter little description about your event") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step1.this);
                }
                else {




                    final ProgressDialog pgDialog;
                    pgDialog = new ProgressDialog(step1.this);
                    pgDialog.setMessage("Please wait...");
                    pgDialog.show();
                    pgDialog.setCanceledOnTouchOutside(false);
                    pgDialog.show();

                    Constants cnts = new Constants();

                    String url = cnts.getUrl();

                    AndroidNetworking.post(url)
                            .addBodyParameter("action", "event_step_1")
                            .addBodyParameter("eventTitle", et_title.getText().toString())
                            .addBodyParameter("compId", getuserid)
                            .addBodyParameter("eventLocation", et_loc.getText().toString())
                            .addBodyParameter("eventDesc", et_discrp.getText().toString())
                            .addBodyParameter("eventStart", tv_startDate.getText().toString())
                            .addBodyParameter("eventEnd", tv_endDate.getText().toString())
                            .addBodyParameter("idInsertedMain","")
                            .addBodyParameter("eventType[]",typeArray.toString())
                            .addBodyParameter("eventActivity[]",activitiesArray.toString())
                            .addBodyParameter("eventShots",et_spots.getText().toString())
                            .addBodyParameter("eventProvince",provices)
                            .addBodyParameter("eventAttraction[]", passingarray)
                            .addBodyParameter("device","android")
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // do anything with response

                                    try {

                                        String getResult = response.getString("result");

                                        if (getResult.equals("success"))
                                        {
                                            pgDialog.dismiss();
                                            String eventid = response.getString("lastid");

                                            SharedPreferences spref = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor= spref.edit();
                                            editor.putString("eventid", eventid);
                                            editor.putString("eventstartingdate",tv_startDate.getText().toString());
                                            editor.putString("eventenddate",tv_endDate.getText().toString());
                                            editor.apply();
                                            Intent intent = new Intent(step1.this, step2.class);
                                            startActivity(intent);
                                            finish();


                                        }



                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    pgDialog.dismiss();
                                    Toast.makeText(step1.this, "Some error occured", Toast.LENGTH_SHORT).show();
                                }
                            });






                }

            }

        });
    }


    public void initiate(){
        bckArro = (ImageView)findViewById(R.id.backarrow);
        et_title = (EditText)findViewById(R.id.titleName);
        et_loc = (EditText)findViewById(R.id.locName);
        et_topAt =(EditText)findViewById(R.id.attrac1);
        et_option = (EditText)findViewById(R.id.optional);
        et_discrp = (EditText)findViewById(R.id.description);
        tv_startDate = (TextView)findViewById(R.id.startDate);
        tv_endDate = (TextView)findViewById(R.id.endDate);
        Next_btn=(Button)findViewById(R.id.EventBtn);
        l1=(LinearLayout)findViewById(R.id.startDateLayout);
        l2=(LinearLayout)findViewById(R.id.endDateLayout);
        l3 = (LinearLayout)findViewById(R.id.top_attrac);
        adMore = (Button)findViewById(R.id.ad_more);
        tv_act = (TextView)findViewById(R.id.tv_activities);
    }


    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }


}
