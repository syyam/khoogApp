package com.android.khoog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.squareup.picasso.Picasso;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class CustomerSetting extends AppCompatActivity {


    String userid;
    String usertype;
    int RESULT_LOAD_IMG = 1;
    ImageView imgshow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_setting);


        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuLayout(R.layout.menu_left_drawer)
                .withDragDistance(180)
                .withRootViewScale(0.7f)
                .withRootViewElevation(20)
                .withRootViewYTranslation(4)
                .withMenuLocked(false)
                .withGravity(SlideGravity.LEFT)
                .withSavedState(savedInstanceState)
                .withContentClickableWhenMenuOpened(true)
                .inject();


        SharedPreferences sp = getSharedPreferences("logininfo", Context.MODE_PRIVATE);

        userid = sp.getString("userid","");
        usertype = sp.getString("usertype", "");

        Constants constants = new Constants();
        String url = constants.getUrl();
        // Image View
        final ProgressDialog pgDialog;
        pgDialog = new ProgressDialog(CustomerSetting.this);
        pgDialog.setMessage("Please wait...");

        pgDialog.show();
        pgDialog.setCanceledOnTouchOutside(false);
        pgDialog.show();



        final ImageView imgView = (ImageView)findViewById(R.id.profile_image_user);


        imgshow = (ImageView)findViewById(R.id.userimgshow);

        imgshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadimage();
            }
        });

        detailsFunction(url, pgDialog);
        getimage(url, pgDialog, imgView);
        getimage(url, pgDialog, imgshow);





    }







    public void detailsFunction(String url, final ProgressDialog pgDialog)
    {

        final TextView tv_memberdate = (TextView)findViewById(R.id.memberdate);

        final TextView tv_name = (TextView)findViewById(R.id.userNameAppears);

        final TextView tv_memberdate2 = (TextView)findViewById(R.id.memberdate2);

        final TextView tv_name2 = (TextView)findViewById(R.id.userNameAppears2);

        final TextView tv_about = (TextView)findViewById(R.id.abouttext);

        final TextView tv_email = (TextView)findViewById(R.id.custemail);

        final TextView tv_phone = (TextView)findViewById(R.id.custphone);

        final TextView tv_gender = (TextView)findViewById(R.id.custgender);

        final TextView tv_dob = (TextView)findViewById(R.id.custdob);

        TextView tv_res = (TextView)findViewById(R.id.custres);

        AndroidNetworking.post(url)
                .addBodyParameter("action", "getuserdetails")
                .addBodyParameter("user_id",userid)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {


                            String getname = response.getString("name");

                            String getabout = response.getString("about");

                            tv_about.setText(getabout);

                            String custemail = response.getString("email");

                            String custphone = response.getString("phone");

                            String custgender = response.getString("gender");

                            String cusdob = response.getString("dob");

                            String newString = cusdob.replace("-"," ");

                            tv_dob.setText(newString);

                            tv_email.setText(custemail);

                            tv_gender.setText(custgender);

                            tv_phone.setText(custphone);

                            String date = response.getString("date");

                            tv_name.setText(getname);

                            tv_memberdate.setText("Member Since "+date);

                            tv_name2.setText(getname);

                            tv_memberdate2.setText("Member Since "+date);



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

                        Toast.makeText(CustomerSetting.this, error.toString(), Toast.LENGTH_SHORT).show();

                        if (pgDialog.isShowing())
                        {
                            pgDialog.dismiss();
                        }
                    }
                });
    }






    public void uploadimage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imagePath = cursor.getString(columnIndex);
                String imgDecodableString = cursor.getString(columnIndex);
                imgshow.setImageURI(selectedImage);
                cursor.close();
                imageUpload(imagePath);

            }

        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uploadimage();

                } else {
                    Toast.makeText(CustomerSetting.this, "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }





    public void imageUpload(String imgUrl)
    {


        final ProgressDialog pgDialog;
        pgDialog = new ProgressDialog(CustomerSetting.this);
        pgDialog.setMessage("Please wait...");
        pgDialog.show();
        pgDialog.setCanceledOnTouchOutside(false);
        pgDialog.show();

        SharedPreferences spinfo = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        String userid = spinfo.getString("userid", "");


        final File imageShow = new File(imgUrl);
        Constants constants = new Constants();
        AndroidNetworking.upload(constants.getUrl())
                .addMultipartFile("image",imageShow)
                .addMultipartParameter("user_id",userid)
                .addMultipartParameter("action","uploadProfileAndroid")
                .setTag("uploadTest")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progres

                        if (bytesUploaded >= totalBytes)
                        {
                            pgDialog.dismiss();
                        }

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(CustomerSetting.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public void getimage(String url, final ProgressDialog pgDialog, final ImageView imgView)
    {
        AndroidNetworking.post(url)
                .addBodyParameter("action", "getProfile")
                .addBodyParameter("user_id",userid)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    public void onResponse(JSONObject response) {

                        try {

                            String result = response.getString("result");


                            if (pgDialog.isShowing())
                            {
                                pgDialog.dismiss();
                            }

                            if (result.equals("success"))
                            {
                                String source = response.getString("image");

                                Picasso.get().load(source).into(imgView);
                                /*Picasso.get().load(source).into(profileImage);*/


                                if (pgDialog.isShowing())
                                {
                                    pgDialog.dismiss();
                                }



                            }else{


                                String newsource = "http://khoog.com/images/Male.jpg";
                                Picasso.get().load(newsource).into(imgView);

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
                        Toast.makeText(CustomerSetting.this, "Some error occured", Toast.LENGTH_SHORT).show();
                        if (pgDialog.isShowing())
                        {
                            pgDialog.dismiss();
                        }
                    }
                });
    }





}
