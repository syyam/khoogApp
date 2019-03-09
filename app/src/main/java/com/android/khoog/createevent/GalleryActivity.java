package com.android.khoog.createevent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.ImageAdapter;
import com.android.khoog.R;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.nispok.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    ImageView img;
    Button GAlleryButton;
    int RESULT_LOAD_IMG = 1;

    GridView layout;

    Boolean imageUpload = false;

    private ArrayList<Bitmap> bitmapList;

    SharedPreferences spevent, logininfo;
    String eventid, userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        spevent  = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);

        layout = (GridView)findViewById(R.id.gridview);

        eventid = spevent.getString("eventid","");

        bitmapList = new ArrayList<Bitmap>();

        logininfo = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        userid = logininfo.getString("userid", "");

        img = (ImageView)findViewById(R.id.backarrowstep4);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GalleryActivity.this, step5.class);
                startActivity(intent);
                finish();
            }
        });


        GAlleryButton = (Button)findViewById(R.id.addImage);

        GAlleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                uploadImage();



            }
        });


        Button nextBtn = (Button)findViewById(R.id.pBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageUpload == false)
                {
                    Snackbar.with(getApplicationContext()) // context
                            .text("You must upload at least one image") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(GalleryActivity.this);
                }else{


                    Intent intent = new Intent(GalleryActivity.this, Bookingtype.class);
                    startActivity(intent);
                    finish();


                }

            }
        });
    }

    private void uploadImage() {



        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                cursor.close();
                imageUpload(imagePath);

            }

        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }



    public void imageUpload(String imgUrl)
    {


        final ProgressDialog pgDialog;
        pgDialog = new ProgressDialog(GalleryActivity.this);
        pgDialog.setMessage("Please wait...");
        pgDialog.show();
        pgDialog.setCanceledOnTouchOutside(false);
        pgDialog.show();



        final File imageShow = new File(imgUrl);
        Constants constants = new Constants();
        AndroidNetworking.upload(constants.getUrl())
                .addMultipartFile("eventImage",imageShow)
                .addMultipartParameter("comp_id",userid)
                .addMultipartParameter("action","eventGallery")
                .addMultipartParameter("eventid",eventid)
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
                        try {

                            String getimage = response.getString("filename");

                            final ImageView imgView = new ImageView(GalleryActivity.this);

                            imageUpload = true;

                            AndroidNetworking.get(getimage)
                                    .setTag("imageRequestTag")
                                    .setPriority(Priority.MEDIUM)
                                    .setBitmapMaxHeight(100)
                                    .setBitmapMaxWidth(100)
                                    .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                    .build()
                                    .getAsBitmap(new BitmapRequestListener() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            // do anything with bitm
                                            bitmapList.add(bitmap);
                                            layout.setAdapter(new ImageAdapter(GalleryActivity.this, bitmapList));
                                        }
                                        @Override
                                        public void onError(ANError error) {
                                            // handle error
                                            Toast.makeText(GalleryActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(GalleryActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
