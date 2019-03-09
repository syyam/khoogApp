package com.android.khoog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

public class CompanyHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home_page);


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
    }
}
