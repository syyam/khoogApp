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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.khoog.Constants.Constants;
import com.android.khoog.Constants.SpinnerAdapter;
import com.android.khoog.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.nispok.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class step2 extends AppCompatActivity {
    Button btn1;
    ImageView back;
    EditText compnayN,aboutC,compnayAdd,phonCode;
    String sharedid,f1,f2,ph,usemail;

    ProgressDialog pgDialog;

    String compnytype[] = {"Travel Agent","Tour Operator"} ;
    String positionStringCompany[] = {"Owner","Event Manager","Tour Guide"};
    String positionStringIndividual[] = {"Event Manager","Tour Organizer"};
    String become_cities[] = { "Ahmadpur East"," Ahmed Nager Chatha"," Ali Khan Abad"," Alipur"," Arifwala"," Attock"," Bhera"," Bhalwal"," Bahawalnagar"," Bahawalpur"," Bhakkar"," Burewala"," Chillianwala"," Choa Saidanshah"," Chakwal"," Chak Jhumra"," Chichawatni"," Chiniot"," Chishtian"," Chunian"," Dajkot"," Daska"," Davispur"," Darya Khan"," Dera Ghazi Khan"," Dhaular"," Dina"," Dinga"," Dhudial Chakwal"," Dipalpur"," Faisalabad"," Fateh Jang"," Ghakhar Mandi"," Gojra"," Gujranwala"," Gujrat"," Gujar Khan"," Harappa"," Hafizabad"," Haroonabad"," Hasilpur"," Haveli Lakha"," Jalalpur Jattan"," Jampur"," Jaranwala"," Jhang"," Jhelum"," Kallar Syedan"," Kalabagh"," Karor Lal Esan"," Kasur"," Kamalia"," Kamoke"," Khanewal"," Khanpur"," Khanqah Sharif"," Kharian"," Khushab"," Kot Adu"," Jauharabad"," Lahore"," Islamabad"," Lalamusa"," Layyah"," Lawa Chakwal"," Liaquat Pur"," Lodhran"," Malakwal"," Mamoori"," Mailsi"," Mandi Bahauddin"," Mian Channu"," Mianwali"," Miani"," Multan"," Murree"," Muridke"," Mianwali Bangla"," Muzaffargarh"," Narowal"," Nankana Sahib"," Okara"," Renala Khurd"," Pakpattan"," Pattoki"," Pindi Bhattian"," Pind Dadan Khan"," Pir Mahal"," Qaimpur"," Qila Didar Singh"," Rabwah"," Raiwind"," Rajanpur"," Rahim Yar Khan"," Rawalpindi"," Sadiqabad"," Sagri"," Sahiwal"," Sambrial"," Samundri"," Sangla Hill"," Sarai Alamgir"," Sargodha"," Shakargarh"," Sheikhupura"," Shujaabad"," Sialkot"," Sohawa"," Soianwala"," Siranwali"," Tandlianwala"," Talagang"," Taxila"," Toba Tek Singh"," Vehari"," Wah Cantonment"," Wazirabad"," Yazman"," Zafarwal" };
    String pro[]={"Punjab","Sindh","Balochistan","Khyber Pakhtunkhawa","Azad Kashmir","Gilgit Baltistan"};

    String type;

    String[] travelType, composition, comlocation, comloprovnc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
        compnayN =(EditText)findViewById(R.id.cName);
        aboutC = (EditText)findViewById(R.id.abtCmpny);
        compnayAdd = (EditText)findViewById(R.id.offiAdd);
        phonCode = (EditText)findViewById(R.id.fetchphone);

        compnayN.setTypeface(boldfont());
        aboutC.setTypeface(boldfont());
        compnayAdd.setTypeface(boldfont());
        phonCode.setTypeface(boldfont());


        SharedPreferences result = getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        sharedid = result.getString("userid","");
        usemail = result.getString("useremail","");
        f1 = result.getString("fn","");
        f2 =  result.getString("ln","");
        ph = result.getString("phon","");
        type  = result.getString("type", "");
        compnayN.setText(f1 + " "+f2);
        phonCode.setText(ph);

        if (type.equals("indi"))
        {
            compnayN.setHint("Your Name");
            aboutC.setHint("About Yourself");
            compnayAdd.setHint("Your address");
        }else{
            compnayN.setHint("Your Company Name");
            aboutC.setHint("About Your Company");
            compnayAdd.setHint("Official Address");
        }

        Spinner spinner = (Spinner) findViewById(R.id.companySpin);
        SpinnerAdapter arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,compnytype);
        spinner.setAdapter(arrayAdapter);



        travelType = getValueFromSpinner(spinner, arrayAdapter);

        Spinner spinner1 = (Spinner) findViewById(R.id.positionSpin);
        SpinnerAdapter arrayAdapter1;
        if (type.equals("indi"))
        {
            arrayAdapter1 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,positionStringIndividual);
        }else{
            arrayAdapter1 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,positionStringCompany);
        }
        spinner1.setAdapter(arrayAdapter1);

        composition = getValueFromSpinner(spinner1, arrayAdapter1);

        Spinner spinner2 = (Spinner) findViewById(R.id.citySpin);

        SpinnerAdapter arrayAdapter2 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,become_cities);
        spinner2.setAdapter(arrayAdapter2);

        comlocation = getValueFromSpinner(spinner2, arrayAdapter2);

        Spinner spinner3 = (Spinner) findViewById(R.id.province);
        SpinnerAdapter arrayAdapter3 = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,pro);
        spinner3.setAdapter(arrayAdapter3);

        comloprovnc = getValueFromSpinner(spinner3, arrayAdapter3);

        back = (ImageView)findViewById(R.id.backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(step2.this,step1.class);
                startActivity(intent);
            }
        });

        btn1 = (Button)findViewById(R.id.pBtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!compnayN.getText().toString().isEmpty()) {

                    compnayN.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (!aboutC.getText().toString().isEmpty()){

                    aboutC.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (!compnayAdd.getText().toString().isEmpty()){

                    compnayAdd.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (!phonCode.getText().toString().isEmpty()){
                    phonCode.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (compnayN.getText().toString().isEmpty()) {
                    compnayN.setBackgroundResource(R.drawable.error_edittext);
                    compnayN.requestFocus();
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please enter a Company Name") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step2.this);
                } else if (aboutC.getText().toString().isEmpty()){
                    aboutC.setBackgroundResource(R.drawable.error_edittext);
                    aboutC.requestFocus();
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please enter a About Company") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step2.this);
                }else if (compnayAdd.getText().toString().isEmpty()) {
                    compnayAdd.setBackgroundResource(R.drawable.error_edittext);
                    compnayAdd.requestFocus();
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please enter an Official Address") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step2.this);
                }else if (phonCode.getText().toString().isEmpty()) {
                    phonCode.setBackgroundResource(R.drawable.error_edittext);
                    phonCode.requestFocus();
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please enter a Phone Number") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step2.this);
                }
                else {

                    /*Toast.makeText(Step2.this, travelType[0].toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(Step2.this, composition[0].toString(), Toast.LENGTH_SHORT).show();*/

                    pgDialog = new ProgressDialog(step2.this);
                    pgDialog.setMessage("Please wait...");
                    pgDialog.show();
                    pgDialog.setCanceledOnTouchOutside(false);


                    Constants constants = new Constants();

                    AndroidNetworking.post(constants.getUrl())
                            .addBodyParameter("action", "companyinfo")
                            .addBodyParameter("compid", sharedid)
                            .addBodyParameter("comptype",travelType[0] )
                            .addBodyParameter("about", aboutC.getText().toString())
                            .addBodyParameter("office",compnayAdd.getText().toString() )
                            .addBodyParameter("city", comlocation[0])
                            .addBodyParameter("pro", comloprovnc[0])
                            .addBodyParameter("position", composition[0])
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
                                            Intent intent = new Intent(step2.this, step3.class);
                                            startActivity(intent);
                                            finish();
                                        }else{

                                            Snackbar.with(getApplicationContext()) // context
                                                    .text("Something Went Wrong") // text to display
                                                    .color(Color.RED)
                                                    .textColor(Color.WHITE)
                                                    .show(step2.this);

                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onError(ANError error) {
                                    // handle error

                                    Snackbar.with(getApplicationContext()) // context
                                            .text("Something Went Wrong") // text to display
                                            .color(Color.RED)
                                            .textColor(Color.WHITE)
                                            .show(step2.this);

                                }

                            });
                }
            }
        });

    }

    public String[] getValueFromSpinner(Spinner spinner, final ArrayAdapter<String> arrayAdapter)
    {

        final String[] getvalue = new String[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getvalue[0] = arrayAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return getvalue;
    }


    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }
}
