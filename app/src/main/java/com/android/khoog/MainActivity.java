package com.android.khoog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView splashheading1, splashheading2;

    Typeface robotobold, robotolight;

    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        continueBtn = (Button)findViewById(R.id.splashContinueBtn);

        robotobold = Typeface.createFromAsset(getAssets(),  "fonts/robotobold.ttf");

        robotolight = Typeface.createFromAsset(getAssets(),  "fonts/roboto.ttf");

        splashheading1 = (TextView)findViewById(R.id.splashheading1);

        splashheading1.setTypeface(robotobold);

        splashheading2 = (TextView)findViewById(R.id.splashheading2);

        splashheading2.setTypeface(robotobold);

        continueBtn.setTypeface(robotolight);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("logininfo",Context.MODE_PRIVATE);

        String userid = sharedPreferences.getString("userid","");
        String usertype = sharedPreferences.getString("usertype","");
        if (!userid.equals(""))
        {

            if (usertype.equals("company"))
            {

                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
                finish();

            }else{

                Intent intent = new Intent(MainActivity.this, Homepage.class);
                startActivity(intent);
                finish();

            }



        }



    }
}
