package com.android.khoog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.squareup.picasso.Picasso;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class userDashboard extends AppCompatActivity implements UpcomingToursCustomer.OnFragmentInteractionListener, PasttoursCustomer.OnFragmentInteractionListener{

    String userid;
    String usertype;
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);


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

        vp = (ViewPager)findViewById(R.id.db_userviewpager);

        SharedPreferences sp = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        userid = sp.getString("userid","");
        usertype = sp.getString("usertype", "");

        Constants constants = new Constants();
        String url = constants.getUrl();

        // Image View
        final ProgressDialog pgDialog;
        pgDialog = new ProgressDialog(userDashboard.this);
        pgDialog.setMessage("Please wait...");
        pgDialog.show();
        pgDialog.setCanceledOnTouchOutside(false);
        pgDialog.show();


        final ImageView imgView = (ImageView)findViewById(R.id.profile_image_user);
        detailsFunction(url, pgDialog);
        getimage(url, pgDialog, imgView);

        setupViewPager(vp);
        TabLayout DbtabLayout = (TabLayout) findViewById(R.id.usertabs);
        DbtabLayout.setupWithViewPager(vp);

        menushow();

    }


    public void menushow()
    {
        TextView tv_setting = (TextView)findViewById(R.id.setting);

        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userDashboard.this, CustomerSetting.class);
                startActivity(intent);
                finish();
            }
        });
    }



    public void detailsFunction(String url, final ProgressDialog pgDialog)
    {

        final TextView tv_memberdate = (TextView)findViewById(R.id.memberdate);

        final TextView tv_name = (TextView)findViewById(R.id.userNameAppears);

        final TextView tv_show = (TextView)findViewById(R.id.db_username);



        final TextView tv_mem = (TextView)findViewById(R.id.db_membersinceshow);
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

                            String date = response.getString("date");

                            tv_mem.setText(date);

                            tv_show.setText(getname);

                            tv_name.setText(getname);

                            tv_memberdate.setText("Member Since "+date);

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

                        Toast.makeText(userDashboard.this, error.toString(), Toast.LENGTH_SHORT).show();

                        if (pgDialog.isShowing())
                        {
                            pgDialog.dismiss();
                        }
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
                        Toast.makeText(userDashboard.this, "Some error occured", Toast.LENGTH_SHORT).show();
                        if (pgDialog.isShowing())
                        {
                            pgDialog.dismiss();
                        }
                    }
                });
    }

    private void setupViewPager(ViewPager viewPager) {

        userDashboard.ViewPagerAdapter adapter = new userDashboard.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpcomingToursCustomer(), "Upcoming Tours");
        adapter.addFragment(new PasttoursCustomer(), "Past Tours");
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

}
