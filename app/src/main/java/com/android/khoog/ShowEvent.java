package com.android.khoog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowEvent extends AppCompatActivity implements EventDetails.OnFragmentInteractionListener, Itinirary.OnFragmentInteractionListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String eid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        Intent intent = getIntent();
         eid = intent.getStringExtra("eid");


        Constants constants = new Constants();
        final String geturl = constants.getUrl();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final ProgressDialog pgDialog;
        pgDialog = new ProgressDialog(ShowEvent.this);
        pgDialog.setMessage("Please wait...");
        pgDialog.show();
        pgDialog.setCanceledOnTouchOutside(false);
        pgDialog.show();

        AndroidNetworking.post(geturl)
                .addBodyParameter("action", "geteventbyid")
                .addBodyParameter("eventid", eid)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            String title = response.getString("title");
                            initCollapsingToolbar(title);

                            String getimage = response.getString("img");
                            AndroidNetworking.get(getimage)
                                    .setTag("imageRequestTag")
                                    .setPriority(Priority.MEDIUM)
                                    .setBitmapMaxHeight(1200)
                                    .setBitmapMaxWidth(700)
                                    .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                    .build()
                                    .getAsBitmap(new BitmapRequestListener() {
                                        @Override
                                        public void onResponse(Bitmap bitmap) {
                                            // do anything with bitmap
                                            ImageView img = (ImageView)findViewById(R.id.getimage);
                                            img.setImageBitmap(bitmap);
                                            pgDialog.dismiss();
                                        }
                                        @Override
                                        public void onError(ANError error) {
                                            // handle error
                                        }
                                    });




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(ShowEvent.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });








        viewPager = (ViewPager) findViewById(R.id.viewpager1);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(viewPager);




















    }

    private void initCollapsingToolbar(final String titletext) {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(titletext);
                    collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor("#F9AD19"));
                    collapsingToolbar.setBackgroundColor(Color.parseColor("#F9AD19"));
                    collapsingToolbar.setCollapsedTitleTypeface(boldfont());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(titletext);
                    collapsingToolbar.setExpandedTitleColor(Color.parseColor("#F9AD19"));
                    collapsingToolbar.setBackgroundColor(Color.parseColor("#F9AD19"));
                    collapsingToolbar.setExpandedTitleTypeface(boldfont());
                    isShow = false;
                }
            }
        });

    }


    private void setupViewPager(ViewPager viewPager) {
        ShowEvent.ViewPagerAdapter adapter = new ShowEvent.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventDetails(), "Event Details");
        adapter.addFragment(new Itinirary(), "Itinerary");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public String returneid()
    {
        return eid;
    }


    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }
}
