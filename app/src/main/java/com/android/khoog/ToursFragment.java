package com.android.khoog;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ToursFragment extends Fragment {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private ImageView[] dots;
    RecyclerView recyclerView;
    private AdapterAlbum adapter;
    private List<Albm> albmList;
    int globalDots;


    public ToursFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_tours, container, false);

        Constants constants = new Constants();


        final LinearLayout buttonlayout = (LinearLayout)view.findViewById(R.id.buttonlayoutshow);


        AndroidNetworking.post(constants.getUrl())
                .addBodyParameter("action", "getcitiesandroid")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray getarray = response.getJSONArray("cities");


                            for (int i = 0; i < getarray.length(); i++)
                            {
                                String city = getarray.getString(i);

                                Button btn = new Button(getActivity());

                                LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);


                                lp.setMargins(0, 0, 10, 0);

                                btn.setLayoutParams(lp);

                                btn.setText(city);

                                btn.setBackgroundResource(R.drawable.shadowbutton);

                                buttonlayout.addView(btn);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(getActivity().getApplicationContext(), "Some error occured", Toast.LENGTH_SHORT).show();

                    }
                });



















        recyclerView = (RecyclerView) view.findViewById(R.id.rcView);
        albmList = new ArrayList<>();
        adapter = new AdapterAlbum(getContext(),albmList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ToursFragment.GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareAlbums();

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        TextView featuretext = (TextView)view.findViewById(R.id.featurestatus);

        int featured = 1;
        if (featured <= 0)
        {
            viewPager.setVisibility(View.GONE);
            sliderDotspanel.setVisibility(View.GONE);
            featuretext.setVisibility(View.VISIBLE);
        }else{


            AndroidNetworking.post(constants.getUrl())
                    .addBodyParameter("action","getfeatured")
                    .setTag("test")
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            int dotsCount =0;
                            ArrayList<String> stringList = new ArrayList<String>();
                            ArrayList<String> imageList = new ArrayList<String>();
                            ArrayList<String> priceList = new ArrayList<String>();
                            ArrayList<String> compList = new ArrayList<String>();
                            ArrayList<String> idList = new ArrayList<String>();
                            try {

                                JSONArray jsonArray = response.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    dotsCount++;
                                    String getobject = jsonArray.getString(i);
                                    JSONObject jsonObject = new JSONObject(getobject);
                                    String gettitle = jsonObject.getString("title");
                                    String getimage = jsonObject.getString("image");
                                    String getprice = jsonObject.getString("price");
                                    getprice = getprice.replace(" ",",");
                                    String compname = jsonObject.getString("comp");
                                    /*title[dotsCount] = gettitle;*/
                                    stringList.add(gettitle);
                                    imageList.add(getimage);
                                    priceList.add(getprice);
                                    compList.add(compname);
                                    String id = jsonObject.getString("id");
                                    idList.add(id);
                                }



                                if (globalDots > 3)
                                {
                                    globalDots = 3;
                                    dotsCount = 3;
                                }else{
                                    globalDots = dotsCount;
                                }

                                if (globalDots > 1)
                                {
                                    addDots(dotsCount);
                                }


                                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getApplicationContext(), imageList, stringList, priceList, compList, idList);
                                viewPager.setAdapter(viewPagerAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        @Override
                        public void onError(ANError anError) {

                            Toast.makeText(getActivity(), anError.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });


        }



        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ToursFragment.MyTimerTask(), 2000, 4000);

        return view;
    }



    public void addDots(final int dotscount)
    {

        dots = new ImageView[dotscount];
        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(getActivity().getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            if (getActivity() != null)
            {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (globalDots == 3)
                        {

                            if (viewPager.getCurrentItem() == 0) {
                                try{
                                    viewPager.setCurrentItem(1);

                                }catch (ArrayIndexOutOfBoundsException e)
                                {
                                    Toast.makeText(getActivity().getApplicationContext(), "No item found", Toast.LENGTH_SHORT).show();
                                }

                            } else if (viewPager.getCurrentItem() == 1) {
                                viewPager.setCurrentItem(2);
                            } else {
                                viewPager.setCurrentItem(0);
                            }

                        }else if (globalDots == 2)
                        {
                            if (viewPager.getCurrentItem() == 0) {
                                viewPager.setCurrentItem(1);
                            }else {
                                viewPager.setCurrentItem(0);
                            }
                        }else if (globalDots == 1)
                        {
                            viewPager.setCurrentItem(0);
                        }



                    }
                });

            }


        }
    }

    private void prepareAlbums() {






        Constants constants = new Constants();

        AndroidNetworking.post(constants.getUrl())
                .addBodyParameter("action","getnonfeaturedevents")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int dotsCount =0;
                        ArrayList<String> stringList = new ArrayList<String>();
                        ArrayList<String> imageList = new ArrayList<String>();
                        ArrayList<String> priceList = new ArrayList<String>();
                        ArrayList<String> compList = new ArrayList<String>();
                        ArrayList<String> idList = new ArrayList<String>();
                        try {

                            JSONArray jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                dotsCount++;
                                String getobject = jsonArray.getString(i);
                                JSONObject jsonObject = new JSONObject(getobject);
                                String gettitle = jsonObject.getString("title");
                                String getimage = jsonObject.getString("image");
                                String getprice = jsonObject.getString("price");
                                String compname = jsonObject.getString("comp");
                                    /*title[dotsCount] = gettitle;*/
                                stringList.add(gettitle);
                                imageList.add(getimage);
                                priceList.add(getprice);
                                compList.add(compname);
                                String id = jsonObject.getString("id");
                                idList.add(id);
                            }

                            int[] covers = {R.drawable.khoog,R.drawable.khoog,R.drawable.khoog,R.drawable.khoog,R.drawable.khoog,R.drawable.khoog,R.drawable.khoog,R.drawable.khoog,R.drawable.khoog,R.drawable.khoog,};

                            for (int i = 0 ; i < stringList.size(); i++)
                            {
                                Albm a = new Albm(stringList.get(i), imageList.get(i), priceList.get(i), compList.get(i), idList.get(i));
                                albmList.add(a);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(getActivity(), anError.toString(), Toast.LENGTH_SHORT).show();

                    }
                });








        adapter.notifyDataSetChanged();
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    // Converting dp to pixel
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }



}