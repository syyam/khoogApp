package com.android.khoog.createevent;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.Constants.SpinnerAdapter;
import com.android.khoog.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class step3 extends AppCompatActivity {

    String cities[] = { "Ahmadpur East"," Ahmed Nager Chatha"," Ali Khan Abad"," Alipur"," Arifwala"," Attock"," Bhera"," Bhalwal"," Bahawalnagar"," Bahawalpur"," Bhakkar"," Burewala"," Chillianwala"," Choa Saidanshah"," Chakwal"," Chak Jhumra"," Chichawatni"," Chiniot"," Chishtian"," Chunian"," Dajkot"," Daska"," Davispur"," Darya Khan"," Dera Ghazi Khan"," Dhaular"," Dina"," Dinga"," Dhudial Chakwal"," Dipalpur"," Faisalabad"," Fateh Jang"," Ghakhar Mandi"," Gojra"," Gujranwala"," Gujrat"," Gujar Khan"," Harappa"," Hafizabad"," Haroonabad"," Hasilpur"," Haveli Lakha"," Jalalpur Jattan"," Jampur"," Jaranwala"," Jhang"," Jhelum"," Kallar Syedan"," Kalabagh"," Karor Lal Esan"," Kasur"," Kamalia"," Kamoke"," Khanewal"," Khanpur"," Khanqah Sharif"," Kharian"," Khushab"," Kot Adu"," Jauharabad"," Lahore"," Islamabad"," Lalamusa"," Layyah"," Lawa Chakwal"," Liaquat Pur"," Lodhran"," Malakwal"," Mamoori"," Mailsi"," Mandi Bahauddin"," Mian Channu"," Mianwali"," Miani"," Multan"," Murree"," Muridke"," Mianwali Bangla"," Muzaffargarh"," Narowal"," Nankana Sahib"," Okara"," Renala Khurd"," Pakpattan"," Pattoki"," Pindi Bhattian"," Pind Dadan Khan"," Pir Mahal"," Qaimpur"," Qila Didar Singh"," Rabwah"," Raiwind"," Rajanpur"," Rahim Yar Khan"," Rawalpindi"," Sadiqabad"," Sagri"," Sahiwal"," Sambrial"," Samundri"," Sangla Hill"," Sarai Alamgir"," Sargodha"," Shakargarh"," Sheikhupura"," Shujaabad"," Sialkot"," Sohawa"," Soianwala"," Siranwali"," Tandlianwala"," Talagang"," Taxila"," Toba Tek Singh"," Vehari"," Wah Cantonment"," Wazirabad"," Yazman"," Zafarwal" };
    String additional_strng[]={"Accomodation","Meal","Team","Health","Transportation","Others"};
    Button nextbtn,adM, removeService, additionalAddMore, removeAdditional;
    ImageView btnBack;
    LinearLayout secondlayout2, layout3, layout4, layout5;

    JSONArray pickuparray, pickupStartingDate, pickuptimeArray, pickupPriceArray;

    String location1, location2, location3, location4, location5;

    SharedPreferences sp;

    int mHour, mMin;

    LinearLayout addmorelayout1, addmorelayout2, addmorelayout3, addmorelayout4, addmorelayout5;


    String AdditionalAccomodation1, AdditionalAccomodation2, AdditionalAccomodation3, AdditionalAccomodation4, AdditionalAccomodation5;

    JSONArray additionalSpinner, additionalPrice, additionalDesp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step8);



        pickuparray = new JSONArray();

        pickuptimeArray = new JSONArray();

        pickupPriceArray = new JSONArray();

        additionalSpinner = new JSONArray();

        additionalPrice = new JSONArray();

        additionalDesp = new JSONArray();

        pickupStartingDate = new JSONArray();



        secondlayout2 = (LinearLayout)findViewById(R.id.secondLayout);
        layout3 = (LinearLayout)findViewById(R.id.thirdLayout);
        layout4 = (LinearLayout)findViewById(R.id.forthLayout);
        layout5 = (LinearLayout)findViewById(R.id.fifthLayout);
        removeService = (Button)findViewById(R.id.pickupremoveone);

        final Spinner spinner = (Spinner) findViewById(R.id.locationSpin);
        final SpinnerAdapter arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,cities);
        spinner.setAdapter(arrayAdapter);

        Spinner spinner1 = (Spinner)findViewById(R.id.locationSpin1);
        final SpinnerAdapter locationadapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,cities);
        spinner1.setAdapter(locationadapter);


        Spinner spinner2 = (Spinner)findViewById(R.id.locationSpin2);
        final SpinnerAdapter locationadapter2 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,cities);
        spinner2.setAdapter(locationadapter2);



        Spinner spinner3 = (Spinner)findViewById(R.id.locationSpin3);
        final SpinnerAdapter locationadapter3 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,cities);
        spinner3.setAdapter(locationadapter3);


        Spinner spinner4 = (Spinner)findViewById(R.id.locationSpin4);
        final SpinnerAdapter locationadapter4 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,cities);
        spinner4.setAdapter(locationadapter4);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location2 = locationadapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location3 = locationadapter2.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location4 = locationadapter3.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location5 = locationadapter4.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        // Additional Spinner 1

        Spinner spinner_Acco = (Spinner) findViewById(R.id.additionalSpin);
        final SpinnerAdapter arrayAdapter1 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,additional_strng);
        spinner_Acco.setAdapter(arrayAdapter1);

        spinner_Acco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                AdditionalAccomodation1 = arrayAdapter1.getItem(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Spinner Accomodation 2

        Spinner spinner_Acco2 = (Spinner) findViewById(R.id.additionalSpin1);
        final SpinnerAdapter arrayAdapter2 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,additional_strng);
        spinner_Acco2.setAdapter(arrayAdapter2);

        spinner_Acco2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                AdditionalAccomodation2 = arrayAdapter2.getItem(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        // Spinner Accomodation 3


        Spinner spinner_Acco3 = (Spinner) findViewById(R.id.additionalSpin2);
        final SpinnerAdapter arrayAdapter3 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,additional_strng);
        spinner_Acco3.setAdapter(arrayAdapter3);

        spinner_Acco3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                AdditionalAccomodation3 = arrayAdapter3.getItem(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        // Spinner Accomodation 4


        Spinner spinner_Acco4 = (Spinner) findViewById(R.id.additionalSpin3);
        final SpinnerAdapter arrayAdapter4 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,additional_strng);
        spinner_Acco4.setAdapter(arrayAdapter4);

        spinner_Acco4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                AdditionalAccomodation4 = arrayAdapter3.getItem(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner Accomodation 5


        Spinner spinner_Acco5 = (Spinner) findViewById(R.id.additionalSpin4);
        final SpinnerAdapter arrayAdapter5 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,additional_strng);
        spinner_Acco5.setAdapter(arrayAdapter5);

        spinner_Acco5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                AdditionalAccomodation5 = arrayAdapter5.getItem(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                location1 = arrayAdapter.getItem(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnBack = (ImageView)findViewById(R.id.backarrow);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(step3.this,step2.class);
                startActivity(intent);
                finish();
            }
        });


        sp = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);

        String getStartingDate = sp.getString("eventstartingdate", "");
        final String eventid = sp.getString("eventid","");

        final EditText locationDate1 = (EditText)findViewById(R.id.locationDate);
        locationDate1.setText(getStartingDate);

        final EditText locationDate2 = (EditText)findViewById(R.id.locationDate1);
        locationDate2.setText(getStartingDate);

        final EditText locationDate3 = (EditText)findViewById(R.id.locationDate2);
        locationDate3.setText(getStartingDate);

        final EditText locationDate4 = (EditText)findViewById(R.id.locationDate3);
        locationDate4.setText(getStartingDate);

        final EditText pickupPrice = (EditText)findViewById(R.id.locationPrice);
        final EditText pickupPrice1 = (EditText)findViewById(R.id.locationPrice1);
        final EditText pickupPrice2 = (EditText)findViewById(R.id.locationPrice2);
        final EditText pickupPrice3 = (EditText)findViewById(R.id.locationPrice3);
        final EditText pickupPrice4 = (EditText)findViewById(R.id.locationPrice4);

        final EditText locationDate5 = (EditText)findViewById(R.id.locationDate4);
        locationDate5.setText(getStartingDate);

        final EditText timeget1 = (EditText)findViewById(R.id.locationTime);
        final EditText timeget2 = (EditText)findViewById(R.id.locationTime1);
        final EditText timeget3 = (EditText)findViewById(R.id.locationTime2);
        final EditText timeget4 = (EditText)findViewById(R.id.locationTime3);
        final EditText timeget5 = (EditText)findViewById(R.id.locationTime4);
        // Finish next to button

        timeget3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog tmdialog;
                tmdialog = new TimePickerDialog(step3.this, new TimePickerDialog.OnTimeSetListener() {
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

                        timeget3.setText(exactTime);

                    }
                }, mHour, mMin, false);
                tmdialog.show();


            }
        });

        timeget4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog tmdialog;
                tmdialog = new TimePickerDialog(step3.this, new TimePickerDialog.OnTimeSetListener() {
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

                        timeget4.setText(exactTime);

                    }
                }, mHour, mMin, false);
                tmdialog.show();


            }
        });

        timeget5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog tmdialog;
                tmdialog = new TimePickerDialog(step3.this, new TimePickerDialog.OnTimeSetListener() {
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

                        timeget5.setText(exactTime);

                    }
                }, mHour, mMin, false);
                tmdialog.show();


            }
        });

        timeget2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog tmdialog;
                tmdialog = new TimePickerDialog(step3.this, new TimePickerDialog.OnTimeSetListener() {
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

                        timeget2.setText(exactTime);

                    }
                }, mHour, mMin, false);
                tmdialog.show();


            }
        });

        timeget1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog tmdialog;
                tmdialog = new TimePickerDialog(step3.this, new TimePickerDialog.OnTimeSetListener() {
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

                        timeget1.setText(exactTime);

                    }
                }, mHour, mMin, false);
                tmdialog.show();
            }
        });

        /*
            *Addinotla Layout Ending And Starting
            * Will Be Started Here
            * All Linear Layout will be declared here using findViewById
         */

        // Layout 1

        addmorelayout1 = (LinearLayout)findViewById(R.id.additional_layout);
        addmorelayout2 = (LinearLayout)findViewById(R.id.additional_layout1);
        addmorelayout3 = (LinearLayout)findViewById(R.id.additional_layout2);
        addmorelayout4 = (LinearLayout)findViewById(R.id.additional_layout3);
        addmorelayout5 = (LinearLayout)findViewById(R.id.additional_layout4);


        additionalAddMore = (Button)findViewById(R.id.additionalAddMore);

        additionalAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addmorelayout2.getVisibility() == View.GONE)
                {
                    addmorelayout2.setVisibility(View.VISIBLE);
                    removeAdditional.setVisibility(View.VISIBLE);
                }else if (addmorelayout3.getVisibility() == View.GONE)
                {
                    addmorelayout3.setVisibility(View.VISIBLE);
                }else if (addmorelayout4.getVisibility() == View.GONE)
                {
                    addmorelayout4.setVisibility(View.VISIBLE);
                }else if (addmorelayout5.getVisibility() == View.GONE)
                {
                    addmorelayout5.setVisibility(View.VISIBLE);
                }
            }
        });




        final EditText additionalP1 = (EditText)findViewById(R.id.additionalPrice);
        final EditText additionalP2 = (EditText)findViewById(R.id.additionalPrice1);
        final EditText additionalP3 = (EditText)findViewById(R.id.additionalPrice2);
        final EditText additionalP4 = (EditText)findViewById(R.id.additionalPrice3);
        final EditText additionalP5 = (EditText)findViewById(R.id.additionalPrice4);


        final EditText additionalDesp1 = (EditText)findViewById(R.id.additionalDescr);
        final EditText additionalDesp2 = (EditText)findViewById(R.id.additionalDescr1);
        final EditText additionalDesp3 = (EditText)findViewById(R.id.additionalDescr2);
        final EditText additionalDesp4 = (EditText)findViewById(R.id.additionalDescr3);
        final EditText additionalDesp5 = (EditText)findViewById(R.id.additionalDescr4);

        /// End Here



        nextbtn = (Button)findViewById(R.id.priceEventBtn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final ProgressDialog pgDialog;
                pgDialog = new ProgressDialog(step3.this);
                pgDialog.setMessage("Please wait...");
                pgDialog.show();
                pgDialog.setCanceledOnTouchOutside(false);
                pgDialog.show();



                JSONArray timeArray = new JSONArray();

                if (location1 != null)
                {
                    pickuparray.put(location1);
                    pickupStartingDate.put(locationDate1.getText().toString());
                    pickupPriceArray.put(pickupPrice.getText().toString());
                    timeArray.put(timeget1.getText().toString());
                }

                if (location2 != null)
                {
                    pickuparray.put(location2);
                    pickupStartingDate.put(locationDate2.getText().toString());
                    pickupPriceArray.put(pickupPrice1.getText().toString());
                    timeArray.put(timeget2.getText().toString());
                }
                if (location3 != null)
                {
                    pickuparray.put(location3);
                    pickupStartingDate.put(locationDate3.getText().toString());
                    pickupPriceArray.put(pickupPrice2.getText().toString());
                    timeArray.put(timeget3.getText().toString());
                }
                if (location4 != null)
                {
                    pickuparray.put(location4);
                    pickupStartingDate.put(locationDate4.getText().toString());
                    pickupPriceArray.put(pickupPrice3.getText().toString());
                    timeArray.put(timeget4.getText().toString());
                }
                if (location5 != null)
                {
                    pickuparray.put(location5);
                    pickupStartingDate.put(locationDate5.getText().toString());
                    pickupPriceArray.put(pickupPrice4.getText().toString());
                    timeArray.put(timeget5.getText().toString());
                }






                if (AdditionalAccomodation1 != null)
                {
                    additionalSpinner.put(AdditionalAccomodation1);
                    additionalPrice.put(additionalP1.getText().toString());
                    additionalDesp.put(additionalDesp1.getText().toString());
                }

                if (AdditionalAccomodation2 != null)
                {
                    additionalSpinner.put(AdditionalAccomodation2);
                    additionalPrice.put(additionalP2.getText().toString());
                    additionalDesp.put(additionalDesp2.getText().toString());
                }

                if (AdditionalAccomodation3 != null)
                {
                    additionalSpinner.put(AdditionalAccomodation3);
                    additionalPrice.put(additionalP3.getText().toString());
                    additionalDesp.put(additionalDesp3.getText().toString());
                }

                if (AdditionalAccomodation4 != null)
                {
                    additionalSpinner.put(AdditionalAccomodation4);
                    additionalPrice.put(additionalP4.getText().toString());
                    additionalDesp.put(additionalDesp4.getText().toString());
                }

                if (AdditionalAccomodation5 != null)
                {
                    additionalSpinner.put(AdditionalAccomodation5);
                    additionalPrice.put(additionalP5.getText().toString());
                    additionalDesp.put(additionalDesp5.getText().toString());
                }




                // Sending Request To Api

                SharedPreferences spinfo = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
                String userid = spinfo.getString("userid", "");


                Constants cnts = new Constants();
                String url = cnts.getUrl();
                AndroidNetworking.post(url)
                        .addBodyParameter("action", "eventPricing")
                        .addBodyParameter("pickup[]",pickuparray.toString())
                        .addBodyParameter("pickupDate[]",pickupStartingDate.toString())
                        .addBodyParameter("pickupTime[]",timeArray.toString())
                        .addBodyParameter("pickupPerson[]",pickupPriceArray.toString())
                        .addBodyParameter("additionalCost[]",additionalSpinner.toString())
                        .addBodyParameter("additionalDesc[]",additionalDesp.toString())
                        .addBodyParameter("device","android")
                        .addBodyParameter("additionalPerson[]",additionalPrice.toString())
                        .addBodyParameter("user_id",userid)
                        .addBodyParameter("event_id",eventid)
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
                                        Intent intent = new Intent(step3.this, step4.class);
                                        startActivity(intent);
                                        finish();


                                    }else{
                                        Toast.makeText(step3.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                        if (pgDialog.isShowing())
                                        {
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
                                Toast.makeText(step3.this, "Some error occured", Toast.LENGTH_SHORT).show();
                            }
                        });










            }
        });

        adM=(Button)findViewById(R.id.pickupLocationAddMore);
        adM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (secondlayout2.getVisibility() == View.GONE)
                {
                    secondlayout2.setVisibility(View.VISIBLE);

                    removeService.setVisibility(View.VISIBLE);

                }else if (layout3.getVisibility() == View.GONE)
                {
                    layout3.setVisibility(View.VISIBLE);
                }else if (layout4.getVisibility() == View.GONE)
                {
                    layout4.setVisibility(View.VISIBLE);

                }else if (layout5.getVisibility() == View.GONE)
                {
                    layout5.setVisibility(View.VISIBLE);
                }



            }
        });

        removeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (layout5.getVisibility() == View.VISIBLE)
                {
                    layout5.setVisibility(View.GONE);

                }else if (layout4.getVisibility() == View.VISIBLE)
                {
                    layout4.setVisibility(View.GONE);
                }else if (layout3.getVisibility() == View.VISIBLE)
                {
                    layout3.setVisibility(View.GONE);
                }
                else if (secondlayout2.getVisibility() == View.VISIBLE)
                {
                    secondlayout2.setVisibility(View.GONE);
                    removeService.setVisibility(View.GONE);
                }
            }
        });


        removeAdditional = (Button)findViewById(R.id.additionalRemoveService);
        removeAdditional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addmorelayout5.getVisibility() == View.VISIBLE)
                {
                    addmorelayout5.setVisibility(View.GONE);
                }else if (addmorelayout4.getVisibility() == View.VISIBLE)
                {
                    addmorelayout4.setVisibility(View.GONE);
                }else if (addmorelayout3.getVisibility() == View.VISIBLE)
                {
                    addmorelayout3.setVisibility(View.GONE);
                }else if (addmorelayout2.getVisibility() == View.VISIBLE)
                {
                    addmorelayout2.setVisibility(View.GONE);
                    removeAdditional.setVisibility(View.GONE);

                }


            }
        });


    }
}
