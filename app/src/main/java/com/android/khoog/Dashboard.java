package com.android.khoog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import com.android.khoog.companydashboard.Pasttours;
import com.android.khoog.companydashboard.Reviews;
import com.android.khoog.companydashboard.Rules;
import com.android.khoog.companydashboard.UpcomingTours;
import com.android.khoog.createevent.step1;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.squareup.picasso.Picasso;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements UpcomingTours.OnFragmentInteractionListener, Pasttours.OnFragmentInteractionListener, Reviews.OnFragmentInteractionListener {
    private TabLayout DbtabLayout;
    private ViewPager Dbvp;
    private static int RESULT_LOAD_IMG = 1;
    ImageView dbImg;
    TextView tv_name, tv_subtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuLayout(R.layout.company_menu)
                .withDragDistance(180)
                .withRootViewScale(0.7f)
                .withRootViewElevation(20)
                .withRootViewYTranslation(4)
                .withMenuLocked(false)
                .withGravity(SlideGravity.LEFT)
                .withSavedState(savedInstanceState)
                .withContentClickableWhenMenuOpened(true)
                .inject();
        final ImageView profileImage = (ImageView)findViewById(R.id.profile_image) ;

        TextView tv = (TextView)findViewById(R.id.createevent);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, step1.class);
                startActivity(intent);
                finish();
            }
        });

        final SharedPreferences sppref = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        TextView tvlogout = (TextView)findViewById(R.id.logout);
        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sppref.edit().clear().commit();
                Intent intent = new Intent(Dashboard.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });








        dbImg = (ImageView)findViewById(R.id.companyprofileimage);
        dbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimage();
            }
        });

        tv_name = (TextView)findViewById(R.id.Db_companyname);
        tv_subtitle = (TextView)findViewById(R.id.subTitle);

        Dbvp = (ViewPager) findViewById(R.id.Db_companyViewpager);
        setupViewPager(Dbvp);

        DbtabLayout = (TabLayout) findViewById(R.id.Db_company_tabs);
        DbtabLayout.setupWithViewPager(Dbvp);


        SharedPreferences spLogin = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        final String userid = spLogin.getString("userid","");

        Constants cnts = new Constants();

        final String url = cnts.getUrl();
        final ProgressDialog pgDialog;
        pgDialog = new ProgressDialog(Dashboard.this);
        pgDialog.setMessage("Please wait...");
        pgDialog.show();
        pgDialog.setCanceledOnTouchOutside(false);
        pgDialog.show();


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

                           if (result.equals("success"))
                           {
                               String source = response.getString("image");
                               Picasso.get().load(source).into(dbImg);
                               Picasso.get().load(source).into(profileImage);
                               if (pgDialog.isShowing())
                               {
                                   pgDialog.dismiss();
                               }
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
                        Toast.makeText(Dashboard.this, "Some error occured", Toast.LENGTH_SHORT).show();
                        if (pgDialog.isShowing())
                        {
                            pgDialog.dismiss();
                        }
                    }
                });



        TextView tv_rules = (TextView)findViewById(R.id.rulestext);
        tv_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Rules.class);
                startActivity(intent);
                finish();
            }
        });





        final TextView tv_nameprofile = (TextView)findViewById(R.id.profileCompanyName);
        final TextView tv_position = (TextView)findViewById(R.id.profileEventPosition);


        AndroidNetworking.post(url)
                .addBodyParameter("action", "getProfileAbout")
                .addBodyParameter("user_id",userid)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {

                            String username = response.getString("getname");
                            tv_name.setText(username);

                            tv_nameprofile.setText(username);


                            String subtitle = response.getString("position");
                            tv_subtitle.setText(subtitle);
                            tv_position.setText(subtitle);




                            

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(Dashboard.this, "Some error occured", Toast.LENGTH_SHORT).show();
                    }
                });


    }



    private void setupViewPager(ViewPager viewPager) {
        Dashboard.ViewPagerAdapter adapter = new Dashboard.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpcomingTours(), "Upcoming");
        adapter.addFragment(new Pasttours(), "Past Tours");
        adapter.addFragment(new Reviews(), "Reviews");
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
                String imagePath = cursor.getString(columnIndex);
                String imgDecodableString = cursor.getString(columnIndex);
                dbImg.setImageURI(selectedImage);
                cursor.close();
                imageUpload(imagePath);

            }

        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uploadimage();

                } else {
                    Toast.makeText(Dashboard.this, "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }





    public void imageUpload(String imgUrl)
    {


        final ProgressDialog pgDialog;
        pgDialog = new ProgressDialog(Dashboard.this);
        pgDialog.setMessage("Please wait...");
        pgDialog.show();
        pgDialog.setCanceledOnTouchOutside(false);
        pgDialog.show();

        SharedPreferences spinfo = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        String userid = spinfo.getString("userid", "");


        final File imageShow = new File(imgUrl);
        Constants constants = new Constants();
        AndroidNetworking.upload(constants.getUrl())
                .addMultipartFile("image",imageShow)
                .addMultipartParameter("user_id",userid)
                .addMultipartParameter("action","uploadProfileAndroid")
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
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(Dashboard.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        }

}
