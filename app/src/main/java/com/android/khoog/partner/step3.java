package com.android.khoog.partner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.khoog.R;
import com.nispok.snackbar.Snackbar;

public class step3 extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    Button upbtn,button;
    ImageView frontImg;
    String userid;

    String imagePath = null;

    Boolean imageUploaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3);

        SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        userid = sp.getString("userid","");


        ImageView imgView = (ImageView)findViewById(R.id.backarrow);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(step3.this, step2.class);
                startActivity(intent);
                finish();
            }
        });

        upbtn=(Button)findViewById(R.id.frontside);
        frontImg = (ImageView)findViewById(R.id.frontimage);

        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isReadStoragePermissionGranted() == true)
                {
                    uploadimage();
                }else{

                    Toast.makeText(step3.this, "Permission Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        button = (Button)findViewById(R.id.endBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(Step4Part1.this,Step4Part2.class);
                startActivity(intent);
                finish();*/


               if (imageUploaded == true)
               {

                    Intent in = new Intent(step3.this, step4.class);

                    in.putExtra("image1", imagePath);
                    startActivity(in);
                    finish();





               }else{



                   Snackbar.with(getApplicationContext()) // context
                           .text("Please upload image of your CNIC") // text to display
                           .color(Color.RED)
                           .textColor(Color.WHITE)
                           .show(step3.this);


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
                imagePath = cursor.getString(columnIndex);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                frontImg.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                imageUploaded = true;

            }

        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //resume tasks needing this permission
                    uploadimage();
                }else{
                    Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                }
                break;

            case 3:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //resume tasks needing this permission
                    uploadimage();
                }else{
                    Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
