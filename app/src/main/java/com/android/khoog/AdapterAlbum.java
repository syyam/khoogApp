package com.android.khoog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Adeel on 10/11/2018.
 */

class AdapterAlbum  extends RecyclerView.Adapter<AdapterAlbum.MyViewHolder> {
    private Context mContext;
    private List<Albm> albmList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public TextView price;
        public TextView tv_comp;
        public Button btn;

        public MyViewHolder(final View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            price = (TextView)view.findViewById(R.id.price);
            tv_comp = (TextView)view.findViewById(R.id.comp);
            btn = (Button)view.findViewById(R.id.btn);
            title.setTypeface(boldfont());
            price.setTypeface(boldfont());
            tv_comp.setTypeface(boldfont());
            btn.setTypeface(boldfont());
        }
    }


    public AdapterAlbum(Context mContext, List<Albm> albmList) {
        this.mContext = mContext;
        this.albmList = albmList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Albm albm = albmList.get(position);
        holder.title.setText(albm.getName());
        holder.price.setText(albm.getPrice());
        holder.tv_comp.setText(albm.getComp());
        Picasso.get().load(albm.getThumbnail()).into(holder.thumbnail);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = albm.getId();
                Intent intent = new Intent(mContext, ShowEvent.class);
                intent.putExtra("eid", id);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return albmList.size();
    }

    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(mContext.getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }


}
