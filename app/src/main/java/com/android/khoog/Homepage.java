package com.android.khoog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
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

public class Homepage extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    ImageView iv, imgView;
    TextView ho, tv_name;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    LinearLayout lin1;
    String userid, usertype;
    LinearLayout celayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homepage);

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




        ho=(TextView)findViewById(R.id.home);
        tv_name = (TextView)findViewById(R.id.userNameAppears);


        TextView tv_setting = (TextView)findViewById(R.id.setting);

        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, CustomerSetting.class);
                startActivity(intent);
                finish();
            }
        });


        SharedPreferences sp = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        userid = sp.getString("userid","");
        usertype = sp.getString("usertype", "");


        final ImageView imgView = (ImageView)findViewById(R.id.profile_image_user);

        TextView tvlogout = (TextView)findViewById(R.id.logout);
        final SharedPreferences sppref = getSharedPreferences("logininfo", Context.MODE_PRIVATE);


        Constants constants = new Constants();
        String url = constants.getUrl();

        // Image View
        final ProgressDialog pgDialog;
        pgDialog = new ProgressDialog(Homepage.this);
        pgDialog.setMessage("Please wait...");
        pgDialog.show();
        pgDialog.setCanceledOnTouchOutside(false);
        pgDialog.show();

        final TextView tv_memberdate = (TextView)findViewById(R.id.memberdate);

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

                        Toast.makeText(Homepage.this, error.toString(), Toast.LENGTH_SHORT).show();



                        if (pgDialog.isShowing())
                        {
                            pgDialog.dismiss();
                        }
                    }
                });










        AndroidNetworking.post(url)
                .addBodyParameter("action", "getProfile")
                .addBodyParameter("user_id",userid)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

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
                        Toast.makeText(Homepage.this, "Some error occured", Toast.LENGTH_SHORT).show();
                        if (pgDialog.isShowing())
                        {
                            pgDialog.dismiss();
                        }
                    }
                });










        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sppref.edit().clear().commit();
                Intent intent = new Intent(Homepage.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });









        TextView tv_dashboard = (TextView)findViewById(R.id.dashboard);
        tv_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, userDashboard.class);
                startActivity(intent);
                finish();

            }
        });



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        Homepage.ViewPagerAdapter adapter = new Homepage.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ToursFragment(), "Tours");
        adapter.addFragment(new DealsFragment(), "Deals");
        adapter.addFragment(new EventHostFragment(), "Event Host");
        viewPager.setAdapter(adapter);
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

    public void uploadimage() {


        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
