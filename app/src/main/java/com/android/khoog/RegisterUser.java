package com.android.khoog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.khoog.Constants.Constants;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nispok.snackbar.Snackbar;
import com.sdsmdg.tastytoast.TastyToast;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterUser extends AppCompatActivity {
    int genderValue;
    RadioButton rb;

    String text="";

    private FirebaseAuth mAuth;

    @BindView(R.id.backarrow) ImageView back;
    @BindView(R.id.fullname) EditText et_name;
    @BindView(R.id.emailid) EditText et_email;
    @BindView(R.id.phone) EditText et_phone;
    @BindView(R.id.regpass) EditText et_password;
    @BindView(R.id.signin) TextView sign;
    @BindView(R.id.radio_grop) RadioGroup radioGroup;
    @BindView(R.id.terms_check) CheckBox checkBox;
    @BindView(R.id.regBtn) Button regBtn;
    @BindView(R.id.createAccountText) TextView tvCreateAccount;
    @BindView(R.id.genderfont) TextView genderfont;
    @BindView(R.id.femaleRadio) RadioButton rbfemale;
    @BindView(R.id.maleRadio) RadioButton rbmale;
    @BindView(R.id.alreadyaccount) TextView alreadyaccount;
    @BindView(R.id.avi)
    AVLoadingIndicatorView loader;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    ProgressDialog pgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        tvCreateAccount.setTypeface(boldfont());
        et_name.setTypeface(boldfont());
        et_email.setTypeface(boldfont());
        et_phone.setTypeface(boldfont());
        sign.setTypeface(boldfont());
        regBtn.setTypeface(boldfont());
        et_password.setTypeface(boldfont());
        checkBox.setTypeface(boldfont());
        genderfont.setTypeface(boldfont());
        rbfemale.setTypeface(boldfont());
        rbmale.setTypeface(boldfont());
        alreadyaccount.setTypeface(boldfont());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterUser.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterUser.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }



    public void regFunc(View view)
    {
        if (!et_name.getText().toString().isEmpty()) {

            et_name.setBackgroundResource(R.drawable.rounded_edittext);
        }

        if (!et_email.getText().toString().isEmpty()) {
            et_email.setBackgroundResource(R.drawable.rounded_edittext);
        }
        if (!et_phone.getText().toString().isEmpty()) {
            et_phone.setBackgroundResource(R.drawable.rounded_edittext);
        }
        if (!et_password.getText().toString().isEmpty()) {
            et_password.setBackgroundResource(R.drawable.rounded_edittext);
        }
        if (et_name.getText().toString().isEmpty()) {

            changeBackgroundAndFocus(et_name);
            showTastyToast("Please enter a Full Name");

        } else if (et_email.getText().toString().isEmpty()) {

            changeBackgroundAndFocus(et_email);
            showTastyToast("Please enter Email address");

        } else if (!Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {

            changeBackgroundAndFocus(et_email);
            showTastyToast("Please enter valid email address");

        } else if (et_phone.getText().toString().isEmpty()) {
            changeBackgroundAndFocus(et_phone);
            showTastyToast("Please enter your mobile number");
        } else if (et_password.getText().toString().isEmpty()) {
            changeBackgroundAndFocus(et_phone);
            showTastyToast("Please enter your mobile number");

        } else if (!checkBox.isChecked()) {

            checkBox.setBackgroundResource(R.drawable.error_edittext);
            checkBox.requestFocus();
            showTastyToast("Please check terms and conditions");

        } else {



            pgDialog = new ProgressDialog(RegisterUser.this);
            pgDialog.setMessage("Please wait...");
            pgDialog.show();
            pgDialog.setCanceledOnTouchOutside(false);

            genderValue = radioGroup.getCheckedRadioButtonId();
            rb = (RadioButton) findViewById(genderValue);


            pgDialog.show();

            Constants constants = new Constants();
            AndroidNetworking.post(constants.getUrl())
                    .addBodyParameter("action", constants.getRegisterAction())
                    .addBodyParameter("fullname", et_name.getText().toString())
                    .addBodyParameter("type", "user")
                    .addBodyParameter("emaill", et_email.getText().toString())
                    .addBodyParameter("phone", et_phone.getText().toString())
                    .addBodyParameter("password", et_password.getText().toString())
                    .addBodyParameter("gender", rb.getText().toString())
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                String result = response.getString("result");

                                if (result.equals("email")) {
                                   changeBackgroundAndFocus(et_email);
                                   showTastyToast("Email address already exsist");
                                    loader.setVisibility(View.GONE);

                                    if (pgDialog.isShowing())
                                    {
                                        pgDialog.dismiss();
                                    }


                                } else if (result.equals("phone")) {

                                    if (pgDialog.isShowing())
                                    {
                                        pgDialog.dismiss();
                                    }

                                    changeBackgroundAndFocus(et_phone);
                                    showTastyToast("Phone number already exsist");
                                    loader.setVisibility(View.GONE);
                                } else {



                                    String completeMobileNumber = et_phone.getText().toString();
                                    sendSms(completeMobileNumber);

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



        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Snackbar.with(getApplicationContext()) // context
                        .text("Some error occured") // text to display
                        .color(Color.RED)
                        .textColor(Color.WHITE)
                        .actionLabelTypeface(boldfont())
                        .show(RegisterUser.this);

                if (pgDialog.isShowing())
                {
                    pgDialog.dismiss();
                }

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                if (pgDialog.isShowing())
                {
                    pgDialog.dismiss();
                }

                Intent intent = new Intent(RegisterUser.this, Verification.class);
                intent.putExtra("code", s);
                startActivity(intent);

            }
        };






    }

    public void sendSms(String number)
    {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, RegisterUser.this , mCallback);

    }


    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }

    public Typeface lightfont()
    {
        Typeface robotolight = Typeface.createFromAsset(getAssets(),  "fonts/roboto.ttf");

        return robotolight;
    }

    public void showTastyToast(String showtext)
    {
        TastyToast.makeText(getApplicationContext(), showtext, TastyToast.LENGTH_LONG, TastyToast.ERROR);
    }

    public void changeBackgroundOfLinear(LinearLayout linearLayout)
    {

        linearLayout.setBackgroundResource(R.drawable.error_background);
        linearLayout.requestFocus();
    }

    public void changeBackgroundAndFocus(EditText et)
    {
        et.setBackgroundResource(R.drawable.error_edittext);
        et.requestFocus();

    }

}
