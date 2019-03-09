package com.android.khoog.partner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class step5 extends AppCompatActivity {

    String img1, img2;

    File frontCnic, backCnic;

    String sharedid, type;

    LinearLayout ntnlay, dtslay;

    String dts, ntn, fb, twitter, instagram;

    EditText adFb,adInstgrm,adTwiter,NTN,Licences;

    Button button;

    ProgressDialog pgDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5);

        Intent getimagesintent = getIntent();
        img1 = getimagesintent.getStringExtra("image1");
        img2 = getimagesintent.getStringExtra("image2");

        ntnlay = (LinearLayout)findViewById(R.id.ntnlayout);
        dtslay = (LinearLayout)findViewById(R.id.dtslayout);

        SharedPreferences result = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        sharedid = result.getString("userid","");
        type  = result.getString("type", "");

        frontCnic = new File(img1);
        backCnic = new File(img2);

        adFb=(EditText)findViewById(R.id.addfb);
        adInstgrm=(EditText)findViewById(R.id.addinsta);
        adTwiter=(EditText)findViewById(R.id.addtwitter);
        NTN = (EditText)findViewById(R.id.addntn);
        Licences =(EditText)findViewById(R.id.addlicence);

        if (type.equals("indi"))
        {
            ntnlay.setVisibility(View.GONE);
            dtslay.setVisibility(View.GONE);
        }else{

            ntnlay.setVisibility(View.VISIBLE);
            dtslay.setVisibility(View.VISIBLE);
        }


        button = (Button)findViewById(R.id.endBtnSocial);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                pgDialog = new ProgressDialog(step5.this);
                pgDialog.setMessage("Please wait...");
                pgDialog.show();
                pgDialog.setCanceledOnTouchOutside(false);


                Constants constants = new Constants();
                AndroidNetworking.upload(constants.getUrl())
                        .addMultipartFile("frontside",frontCnic)
                        .addMultipartFile("file",backCnic)
                        .addMultipartParameter("facebook",adFb.getText().toString())
                        .addMultipartParameter("twitter",adTwiter.getText().toString())
                        .addMultipartParameter("instagram",adInstgrm.getText().toString())
                        .addMultipartParameter("ntn",NTN.getText().toString())
                        .addMultipartParameter("lis",Licences.getText().toString())
                        .addMultipartParameter("action","compverif")
                        .addMultipartParameter("comp_id",sharedid)
                        .setTag("uploadTest")
                        .setPriority(Priority.HIGH)
                        .build()
                        .setUploadProgressListener(new UploadProgressListener() {
                            @Override
                            public void onProgress(long bytesUploaded, long totalBytes) {
                                // do anything with progress

                                long byteslefts = totalBytes - bytesUploaded;

                                if (bytesUploaded >= totalBytes)
                                {

                                    if (pgDialog.isShowing())
                                    {
                                        pgDialog.dismiss();
                                    }
                                    Intent intent = new Intent(step5.this, pdf.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        })
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



                                    }else{

                                        Toast.makeText(step5.this, "Some error occured", Toast.LENGTH_SHORT).show();
                                        pgDialog.dismiss();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Toast.makeText(step5.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });




    }
}
