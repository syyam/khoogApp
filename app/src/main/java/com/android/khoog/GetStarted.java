package com.android.khoog;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.khoog.Constants.Constants;
import com.android.khoog.Constants.SpinnerAdapter;
import com.android.khoog.partner.step1;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetStarted extends AppCompatActivity {
    Button btn;
    ImageView back;
    EditText et_name,et_lname,et_email,et_phone,et_password;
    TextView sign;
    @BindView(R.id.getStartedText) TextView startedheading;
    @BindView(R.id.alreadyAccount) TextView alreadyAccount;
    String cities[] = { "Ahmadpur East"," Ahmed Nager Chatha"," Ali Khan Abad"," Alipur"," Arifwala"," Attock"," Bhera"," Bhalwal"," Bahawalnagar"," Bahawalpur"," Bhakkar"," Burewala"," Chillianwala"," Choa Saidanshah"," Chakwal"," Chak Jhumra"," Chichawatni"," Chiniot"," Chishtian"," Chunian"," Dajkot"," Daska"," Davispur"," Darya Khan"," Dera Ghazi Khan"," Dhaular"," Dina"," Dinga"," Dhudial Chakwal"," Dipalpur"," Faisalabad"," Fateh Jang"," Ghakhar Mandi"," Gojra"," Gujranwala"," Gujrat"," Gujar Khan"," Harappa"," Hafizabad"," Haroonabad"," Hasilpur"," Haveli Lakha"," Jalalpur Jattan"," Jampur"," Jaranwala"," Jhang"," Jhelum"," Kallar Syedan"," Kalabagh"," Karor Lal Esan"," Kasur"," Kamalia"," Kamoke"," Khanewal"," Khanpur"," Khanqah Sharif"," Kharian"," Khushab"," Kot Adu"," Jauharabad"," Lahore"," Islamabad"," Lalamusa"," Layyah"," Lawa Chakwal"," Liaquat Pur"," Lodhran"," Malakwal"," Mamoori"," Mailsi"," Mandi Bahauddin"," Mian Channu"," Mianwali"," Miani"," Multan"," Murree"," Muridke"," Mianwali Bangla"," Muzaffargarh"," Narowal"," Nankana Sahib"," Okara"," Renala Khurd"," Pakpattan"," Pattoki"," Pindi Bhattian"," Pind Dadan Khan"," Pir Mahal"," Qaimpur"," Qila Didar Singh"," Rabwah"," Raiwind"," Rajanpur"," Rahim Yar Khan"," Rawalpindi"," Sadiqabad"," Sagri"," Sahiwal"," Sambrial"," Samundri"," Sangla Hill"," Sarai Alamgir"," Sargodha"," Shakargarh"," Sheikhupura"," Shujaabad"," Sialkot"," Sohawa"," Soianwala"," Siranwali"," Tandlianwala"," Talagang"," Taxila"," Toba Tek Singh"," Vehari"," Wah Cantonment"," Wazirabad"," Yazman"," Zafarwal" };
    @BindView(R.id.aviGetStarted)
    AVLoadingIndicatorView avloader;
    SharedPreferences sharedPreferences;
    ProgressDialog pgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_started);
        ButterKnife.bind(this);
        back = (ImageView)findViewById(R.id.backarrow);
        et_name = (EditText)findViewById(R.id.partner_fname);
        et_lname = (EditText)findViewById(R.id.partner_lname);
        et_email = (EditText)findViewById(R.id.partner_emailid);
        et_phone = (EditText)findViewById(R.id.partner_phone);
        et_password = (EditText)findViewById(R.id.partner_pass);
        sign = (TextView)findViewById(R.id.partner_signin);
        btn = (Button)findViewById(R.id.partner_Btn);

        et_name.setTypeface(boldfont());
        et_lname.setTypeface(boldfont());
        et_email.setTypeface(boldfont());
        et_phone.setTypeface(boldfont());
        et_password.setTypeface(boldfont());
        sign.setTypeface(boldfont());
        btn.setTypeface(boldfont());
        startedheading.setTypeface(boldfont());
        alreadyAccount.setTypeface(boldfont());


        final Spinner spinner = (Spinner) findViewById(R.id.citySpin);

        SpinnerAdapter arrayAdapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_dropdown_item,cities);
        spinner.setAdapter(arrayAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!et_name.getText().toString().isEmpty()){

                    et_name.setBackgroundResource(R.drawable.rounded_edittext);
                }

                if (!et_lname.getText().toString().isEmpty())
                {
                    et_lname.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (!et_email.getText().toString().isEmpty())
                {
                    et_email.setBackgroundResource(R.drawable.rounded_edittext);
                }
                if (!et_phone.getText().toString().isEmpty())
                {
                    et_phone.setBackgroundResource(R.drawable.rounded_edittext);

                    if (!et_password.getText().toString().isEmpty())
                    {
                        et_password.setBackgroundResource(R.drawable.rounded_edittext);
                    }
                }
                if(et_name.getText().toString().isEmpty()) {
                    et_name.setBackgroundResource(R.drawable.error_edittext);
                    et_name.requestFocus();
                    TastyToast.makeText(getApplicationContext(),"Please enter a First Name", TastyToast.LENGTH_LONG,TastyToast.ERROR);
                }else if (et_lname.getText().toString().isEmpty()){

                    et_lname.setBackgroundResource(R.drawable.error_edittext);
                    et_lname.requestFocus();
                    TastyToast.makeText(getApplicationContext(),"Please enter a Last Name", TastyToast.LENGTH_LONG,TastyToast.ERROR);
                }else if (et_email.getText().toString().isEmpty()){

                    et_email.setBackgroundResource(R.drawable.error_edittext);
                    et_email.requestFocus();
                    TastyToast.makeText(getApplicationContext(),"Please enter a Email Address", TastyToast.LENGTH_LONG,TastyToast.ERROR);

                }else if (!Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()){
                    et_email.setBackgroundResource(R.drawable.error_edittext);
                    et_email.requestFocus();
                    TastyToast.makeText(getApplicationContext(),"Please enter a Valid E-Mail Address!", TastyToast.LENGTH_LONG,TastyToast.ERROR);

                }else if (et_phone.getText().toString().isEmpty()){
                    et_phone.setBackgroundResource(R.drawable.error_edittext);
                    et_phone.requestFocus();
                    TastyToast.makeText(getApplicationContext(),"Please enter a Mobile Number", TastyToast.LENGTH_LONG,TastyToast.ERROR);
                }else if (et_password.getText().toString().isEmpty()){

                    et_password.setBackgroundResource(R.drawable.error_edittext);
                    et_password.requestFocus();
                    TastyToast.makeText(getApplicationContext(),"Please enter a Password", TastyToast.LENGTH_LONG,TastyToast.ERROR);
                }

                else {

                    pgDialog = new ProgressDialog(GetStarted.this);
                    pgDialog.setMessage("Please wait...");
                    pgDialog.show();
                    pgDialog.setCanceledOnTouchOutside(false);

                    Constants cnts = new Constants();
                    String url = cnts.getUrl();

                    AndroidNetworking.post(url)
                            .addBodyParameter("action", "register")
                            .addBodyParameter("fullname", et_name.getText().toString()+" "+et_lname.getText().toString())
                            .addBodyParameter("type", "company")
                            .addBodyParameter("emaill", et_email.getText().toString())
                            .addBodyParameter("phone", et_phone.getText().toString())
                            .addBodyParameter("password", et_password.getText().toString())
                            .addBodyParameter("city", spinner.getSelectedItem().toString())
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // do anything with response

                                    try {

                                        String result = response.getString("result");

                                        if (result.equals("email"))
                                        {

                                            if (pgDialog.isShowing())
                                            {
                                                pgDialog.dismiss();
                                            }

                                            avloader.setVisibility(View.GONE);
                                            et_email.setBackgroundResource(R.drawable.error_edittext);
                                            et_email.requestFocus();
                                            TastyToast.makeText(getApplicationContext(),"email already exist",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                        }else if (result.equals("phone")){
                                            if (pgDialog.isShowing())
                                            {
                                                pgDialog.dismiss();
                                            }

                                            et_phone.setBackgroundResource(R.drawable.error_edittext);
                                            et_phone.requestFocus();
                                            TastyToast.makeText(getApplicationContext(),"Phone already exist",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                        }else {
                                            String getid = response.getString("userid");
                                            sharedPreferences = getSharedPreferences("userinfo",Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor= sharedPreferences.edit();
                                            editor.putString("userid",getid);
                                            editor.putString("useremail", et_email.getText().toString());
                                            editor.putString("fn",et_name.getText().toString());
                                            editor.putString("ln",et_lname.getText().toString());
                                            editor.putString("phon",et_phone.getText().toString());
                                            editor.putString("city", spinner.getSelectedItem().toString());
                                            editor.apply();

                                            if (pgDialog.isShowing())
                                            {
                                                pgDialog.dismiss();
                                            }

                                            Intent intent = new Intent(GetStarted.this,step1.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    }
                                    catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                }
                            });
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetStarted.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetStarted.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }


}
