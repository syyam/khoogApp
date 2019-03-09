package com.android.khoog.partner;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.khoog.R;
import com.nispok.snackbar.Snackbar;

public class step4 extends AppCompatActivity {

    String getimage1;

    String getimage2;

    boolean imageUploaded = false;

    Button bckbtn,button;
    ImageView backImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step4);



        final Intent intent = getIntent();
        getimage1 = intent.getStringExtra("image1");



        bckbtn=(Button)findViewById(R.id.backside);
        backImg = (ImageView)findViewById(R.id.bcimg);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimage();
            }
        });
        button = (Button)findViewById(R.id.endCNICBtn);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(step4.this, step3.class);
                startActivity(intent1);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageUploaded == true)
                {


                    Intent intent1 = new Intent(step4.this, step5.class);
                    intent1.putExtra("image1", getimage1);
                    intent1.putExtra("image2", getimage2);
                    startActivity(intent1);
                    finish();


                }else{
                    Snackbar.with(getApplicationContext()) // context
                            .text("Please upload image of your CNIC") // text to display
                            .color(Color.RED)
                            .textColor(Color.WHITE)
                            .show(step4.this);

                }



            }
        });
    }
    public void uploadimage() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == 101)
        {
            if (resultCode == RESULT_OK)
            {
                if (data != null)
                {

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    getimage2 = cursor.getString(columnIndex);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    backImg.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                    imageUploaded = true;

                }
            }
        }


    }

}
