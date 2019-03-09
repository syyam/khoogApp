package com.android.khoog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class SuccessfullyCreate extends AppCompatActivity {
    Button showEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully_create);

        SharedPreferences sp = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);
        final String eventid = sp.getString("eventid", "");

        final ImageView iv =(ImageView)findViewById(R.id.imgcheck);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.anim2);
        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //iv.startAnimation(an2);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //other code here Intent i = new Intent(MainActivity.this,SecondActivity.class);
                        //other code here } }, 5000);
                        Intent i = new Intent(SuccessfullyCreate.this, ShowEvent.class);
                        i.putExtra("eid",eventid);
                        startActivity(i);
                        finish();
                    }
                }, 15000);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        showEvent = (Button)findViewById(R.id.pBtn);
        showEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });

    }

}
