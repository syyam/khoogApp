package com.android.khoog.createevent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.R;
import com.android.khoog.addactivity;
import com.android.khoog.addtransport;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class step5 extends AppCompatActivity {

    Button addAct, addTrans, movetoGallery;


    String day = "1", ittdata;

    SharedPreferences spevent;

    LinearLayout mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step10);

        addAct = (Button)findViewById(R.id.addActivivty);
        addTrans = (Button)findViewById(R.id.addTransportation);
        mainlayout = (LinearLayout)findViewById(R.id.mainlayoutitt);
        movetoGallery = (Button)findViewById(R.id.pBtn);

        movetoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(step5.this, GalleryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        try {
            Intent intent = getIntent();
            String getday = intent.getStringExtra("dayss");
            if (!getday.equals(""))
            {
                day = getday;
            }

        }catch (Exception e)
        {

        }
        // Dates Difference between starting date and ending date




        spevent  = getSharedPreferences("eventinfo", Context.MODE_PRIVATE);
        String date1 = spevent.getString("eventstartingdate","");

        long parseDate = Date.parse(date1);

        String date2 = spevent.getString("eventenddate", "");

        long date2parse = Date.parse(date2);
        long Difference = date2parse - parseDate;
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long elapsedDays = Difference/ daysInMilli;
        Integer getdays = (int)(long) elapsedDays;
        // Closing Difference Date HEre


        // Linear layout of 2 days
        LinearLayout buttonlayout = (LinearLayout)findViewById(R.id.buttondayslayout);
        for (int i = 1; i <= getdays; i++)
        {


            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0 , 10, 0);
            button.setLayoutParams(params);
            String valueDay = "Day "+String.valueOf(i);
            button.setText(valueDay);
            button.setTypeface(boldfont());
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.select_border));
            } else {
                button.setBackground(ContextCompat.getDrawable(this, R.drawable.select_border));
            }
            buttonlayout.addView(button);

            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mainlayout.removeAllViewsInLayout();
                    day = String.valueOf(finalI);
                    infobydata();

                }
            });

        }

        // Ending layout button of days




        addAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intnt = new Intent(step5.this, addactivity.class);
                intnt.putExtra("day", day);
                startActivity(intnt);
                finish();
            }
        });


        addTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(step5.this, addtransport.class);
                intent.putExtra("day", day);
                Toast.makeText(step5.this, day, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });


        infobydata();





    }







    public void infobydata()
    {
        final String eventid = spevent.getString("eventid","");



        final ProgressDialog pgDialog;
        pgDialog = new ProgressDialog(step5.this);
        pgDialog.setMessage("Please wait...");
        pgDialog.show();
        pgDialog.setCanceledOnTouchOutside(false);
        pgDialog.show();



        Constants cnts = new Constants();
        String url = cnts.getUrl();
        AndroidNetworking.post(url)
                .addBodyParameter("action", "EventByDayInformation")
                .addBodyParameter("eventid", eventid)
                .addBodyParameter("day", day)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String result = response.getString("result");

                            if (result.equals("success"))
                            {

                                pgDialog.dismiss();

                                JSONArray array = response.getJSONArray("getdata");
                                int count = 0;
                                for (int i = 0; i < array.length(); i++)
                                {


                                    count++;
                                    String getobj = array.getString(i);

                                    JSONObject objectCheck = new JSONObject(getobj);

                                    String getloc = objectCheck.getString("loc");

                                    String gettitle = objectCheck.getString("title");

                                    String gettype = objectCheck.getString("type");

                                    String gettime = objectCheck.getString("time");


                                    if (gettype.equals("Foods"))
                                    {

                                        int heightLine = dpToPx(80);
                                        int widthline = dpToPx(5);
                                        int textMargin = dpToPx(25);
                                        int leftMargin = dpToPx(48);
                                        int margTopLayout = dpToPx(-30);

                                        LinearLayout linearLayout = new LinearLayout(step5.this);
                                        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightLine);
                                        params.setMargins(leftMargin, margTopLayout, 0, 0);
                                        linearLayout.setLayoutParams(params);
                                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                                        LinearLayout l1 = new LinearLayout(step5.this);
                                        LinearLayoutCompat.LayoutParams params2 = new LinearLayoutCompat.LayoutParams(widthline, heightLine);
                                        l1.setLayoutParams(params2);
                                        l1.setBackgroundResource(R.drawable.line);

                                        TextView tv = new TextView(step5.this);
                                        String foodString = gettitle+" At "+getloc+" ( "+gettime+" )";
                                        tv.setText(foodString);
                                        tv.setTypeface(boldfont());
                                        LinearLayoutCompat.LayoutParams textParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        textParams.setMargins(textMargin, textMargin, 0, 0);
                                        int textSize = dpToPx(4);
                                        tv.setTextSize(textSize);
                                        tv.setTypeface(boldfont());
                                        tv.setLayoutParams(textParams);

                                        linearLayout.addView(l1);
                                        linearLayout.addView(tv);
                                        mainlayout.addView(linearLayout);

                                    }else if (gettype.equals("Departure"))
                                    {
                                        int textMargin = dpToPx(25);
                                        int margTopLayout = dpToPx(-30);
                                        LinearLayout linearLayout = new LinearLayout(step5.this);
                                        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                                        LinearLayout l1 = new LinearLayout(step5.this);
                                        LinearLayoutCompat.LayoutParams params2 = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        params2.setMargins(0, margTopLayout, 0, 0);
                                        l1.setLayoutParams(params2);

                                        l1.setBackgroundResource(R.drawable.circleshape);

                                        TextView tv = new TextView(step5.this);

                                        tv.setTypeface(boldfont());

                                        String makeString = "Departure From "+getloc+"(" +gettime+ ")";

                                        tv.setText(makeString);
                                        int marginleft = dpToPx(-20);
                                        int marginTop = dpToPx(10);
                                        LinearLayoutCompat.LayoutParams textParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        textParams.setMargins(marginleft, marginTop, 0, 0);
                                        int textSize = dpToPx(4);
                                        tv.setTextSize(textSize);
                                        tv.setTypeface(boldfont());
                                        tv.setLayoutParams(textParams);

                                        linearLayout.addView(l1);
                                        linearLayout.addView(tv);
                                        mainlayout.addView(linearLayout);

                                    }else if (gettype.equals("Arrival"))
                                    {
                                        int MarginLeft = dpToPx(35);
                                        int marinBottom = dpToPx(35);
                                        int marginTop = dpToPx(5);
                                        LinearLayout linearLayout = new LinearLayout(step5.this);
                                        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        params.setMargins(MarginLeft, marginTop, 0, marinBottom);
                                        linearLayout.setLayoutParams(params);
                                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                                        LinearLayout l1 = new LinearLayout(step5.this);
                                        LinearLayoutCompat.LayoutParams params2 = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        l1.setLayoutParams(params2);
                                        l1.setBackgroundResource(R.drawable.outlinecircle);
                                        int marginTopText = dpToPx(5);
                                        int textMarginLeft = dpToPx(15);
                                        TextView tv = new TextView(step5.this);
                                        String arrivalString = "Arrival At "+getloc+" ("+gettime+")";
                                        tv.setText(arrivalString);
                                        tv.setTypeface(boldfont());
                                        LinearLayoutCompat.LayoutParams textParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        textParams.setMargins(textMarginLeft, marginTopText, 0, 0);
                                        int textSize = dpToPx(4);
                                        tv.setTextSize(textSize);
                                        tv.setTypeface(boldfont());
                                        tv.setLayoutParams(textParams);

                                        linearLayout.addView(l1);
                                        linearLayout.addView(tv);
                                        mainlayout.addView(linearLayout);
                                    }

                                }

                                if (count <= 0)
                                {
                                    pgDialog.dismiss();
                                    Toast.makeText(step5.this, "No Itinerary Found For This Day", Toast.LENGTH_SHORT).show();
                                }


                            }else{
                                pgDialog.dismiss();
                                Toast.makeText(step5.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pgDialog.dismiss();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(step5.this, "Some error occured", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }


}
