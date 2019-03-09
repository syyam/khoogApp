package com.android.khoog.companydashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.Dashboard;
import com.android.khoog.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Rules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        ImageView img = (ImageView)findViewById(R.id.bckarrow);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Rules.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        final EditText et_rules = (EditText)findViewById(R.id.etrules);

        final ProgressDialog pgDialog;
        pgDialog = new ProgressDialog(Rules.this);
        pgDialog.setMessage("Please wait...");
        pgDialog.show();
        pgDialog.setCanceledOnTouchOutside(false);
        pgDialog.show();


        Constants constants = new Constants();
        final String url = constants.getUrl();






        SharedPreferences spinfo = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        final String userid = spinfo.getString("userid","");


        Button rulesbtn = (Button)findViewById(R.id.rulesBtn);
        rulesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog nd;
                nd = new ProgressDialog(Rules.this);
                nd.setMessage("Please wait...");
                nd.show();
                nd.setCanceledOnTouchOutside(false);
                nd.show();

                AndroidNetworking.post(url)
                        .addBodyParameter("action", "AddRules")
                        .addBodyParameter("user_id",userid)
                        .addBodyParameter("text",et_rules.getText().toString())
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

                                        Toast.makeText(Rules.this, "Rules and Regulation Updated", Toast.LENGTH_SHORT).show();

                                        if (nd.isShowing())
                                        {
                                            nd.dismiss();
                                        }
                                    }else{

                                        if (nd.isShowing())
                                        {
                                            nd.dismiss();
                                        }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    if (nd.isShowing())
                                    {
                                        nd.dismiss();
                                    }
                                }
                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Toast.makeText(Rules.this, "Some error occured", Toast.LENGTH_SHORT).show();
                                if (nd.isShowing())
                                {
                                    nd.dismiss();
                                }
                            }
                        });



            }
        });







        AndroidNetworking.post(url)
                .addBodyParameter("action", "getRules")
                .addBodyParameter("user_id",userid)
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

                                String getrules = response.getString("rules");

                                et_rules.setText(getrules);

                                if (pgDialog.isShowing())
                                {
                                    pgDialog.dismiss();
                                }
                            }else{

                                et_rules.setHint("Write Rules for event");
                                if (pgDialog.isShowing())
                                {
                                    pgDialog.dismiss();
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (pgDialog.isShowing())
                            {
                                pgDialog.dismiss();
                            }
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(Rules.this, "Some error occured", Toast.LENGTH_SHORT).show();
                        if (pgDialog.isShowing())
                        {
                            pgDialog.dismiss();
                        }
                    }
                });



    }
}
