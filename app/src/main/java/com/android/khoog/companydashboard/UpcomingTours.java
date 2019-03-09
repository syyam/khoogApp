package com.android.khoog.companydashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.R;
import com.android.khoog.ShowEvent;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpcomingTours.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpcomingTours#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingTours extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    LinearLayout mainlayout;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UpcomingTours() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingTours.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingTours newInstance(String param1, String param2) {
        UpcomingTours fragment = new UpcomingTours();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_tours, container, false);

        mainlayout = (LinearLayout)view.findViewById(R.id.mainlayoutut);

        SharedPreferences sp = getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        String userid = sp.getString("userid","");

        Constants cnts = new Constants();
        String url = cnts.getUrl();
        AndroidNetworking.post(url)
                .addBodyParameter("action", "EventSearchByUserid")
                .addBodyParameter("userid",userid)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray jsonArray = response.getJSONArray("result");

                            for (int i=0; i < jsonArray.length(); i++)
                            {
                                final String getArray  = jsonArray.getString(i);

                                JSONObject jsonObject = new JSONObject(getArray);
                                String gettitle = jsonObject.getString("title");
                                String getdate = jsonObject.getString("start");
                                final String getid = jsonObject.getString("id");

                                LinearLayout linearLayout = new LinearLayout(getActivity());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                int marginTop = dpToPx(10);
                                int leftMargin = dpToPx(5);
                                lp.setMargins(leftMargin, marginTop, leftMargin, 0);
                                linearLayout.setLayoutParams(lp);

                                linearLayout.setWeightSum(3);

                                TextView tv_title = new TextView(getActivity());
                                tv_title.setText(gettitle);
                                int getDp = dpToPx(7);

                                tv_title.setTextSize(getDp);
                                tv_title.setTypeface(boldfont());

                                LinearLayout l1 = new LinearLayout(getActivity());
                                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
                                l1.setOrientation(LinearLayout.VERTICAL);
                                l1.setLayoutParams(lp1);

                                l1.addView(tv_title);

                                TextView tv_date = new TextView(getActivity());
                                int getDp1 = dpToPx(4);
                                tv_date.setTextSize(getDp1);
                                tv_date.setTypeface(boldfont());

                                tv_date.setText("Starting on "+getdate);
                                l1.addView(tv_date);

                                LinearLayout l2 = new LinearLayout(getActivity());
                                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f);
                                l2.setOrientation(LinearLayout.VERTICAL);
                                l2.setLayoutParams(lp2);

                                Button button = new Button(getActivity());
                                button.setTypeface(boldfont());
                                button.setText("View Details");
                                l2.addView(button);

                                button.setBackgroundResource(R.drawable.rounded_btn);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(), ShowEvent.class);
                                        intent.putExtra("eid",getid);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });

                                linearLayout.addView(l1);
                                linearLayout.addView(l2);
                                button.setTextColor(Color.WHITE);
                                button.setTypeface(boldfont());
                                mainlayout.addView(linearLayout);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }

}
