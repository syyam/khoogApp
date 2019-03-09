package com.android.khoog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Adeel on 10/11/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {


    private Context context;
    private LayoutInflater layoutInflater;
    ArrayList<String> imageList = new ArrayList<String>();
    ArrayList<String> priceList= new ArrayList<String>();
    ArrayList<String> compList = new ArrayList<String>();
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayList<String> idList = new ArrayList<String>();
    public ViewPagerAdapter(Context context, ArrayList<String> imageList, ArrayList<String> stringArrayList, ArrayList<String> priceList, ArrayList<String> compList, ArrayList<String> idList) {
        this.context = context;
        this.imageList = imageList;
        this.stringArrayList = stringArrayList;
        this.priceList = priceList;
        this.compList = compList;
        this.idList = idList;

    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView tv = (TextView)view.findViewById(R.id.title);
        TextView tv_price = (TextView)view.findViewById(R.id.price);
        TextView tv_comp = (TextView)view.findViewById(R.id.comp);
        TextView tv_perperson = (TextView)view.findViewById(R.id.person);
        Button btn = (Button) view.findViewById(R.id.slidebtn);

        TextView tv_ft = (TextView)view.findViewById(R.id.featuredTitle);
        tv_ft.setTypeface(boldfont());


        btn.setTypeface(boldfont());

        tv.setTypeface(boldfont());
        tv_perperson.setTypeface(boldfont());
        tv_comp.setTypeface(boldfont());
        tv_price.setTypeface(boldfont());
        try {
            tv.setText(stringArrayList.get(position));
            tv_price.setText(priceList.get(position));
            Picasso.get().load(imageList.get(position)).into(imageView);
            tv_comp.setText(compList.get(position));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, ShowEvent.class);
                    intent.putExtra("eid", idList.get(position));
                    context.startActivity(intent);


                }
            });
        }catch (IndexOutOfBoundsException e)
        {

        }



        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }


    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(context.getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }


}
