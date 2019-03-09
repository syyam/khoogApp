package com.android.khoog.partner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.android.khoog.LoginActivity;
import com.android.khoog.R;

public class finishRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_registration);

        final ImageView iv =(ImageView)findViewById(R.id.imgcheck);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.anim2);


        Button btn = (Button)findViewById(R.id.wpBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(finishRegistration.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
                        Intent i = new Intent(finishRegistration.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 15000);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
