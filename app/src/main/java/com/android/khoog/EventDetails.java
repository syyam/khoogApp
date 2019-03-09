package com.android.khoog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.khoog.Constants.Constants;
import com.android.khoog.creditcard.CheckOutActivity;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int men, women, child;

    String myDataFromActivity, geturl;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btnlayout;

    private OnFragmentInteractionListener mListener;

    public EventDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetails newInstance(String param1, String param2) {
        EventDetails fragment = new EventDetails();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View view = inflater.inflate(R.layout.fragment_event_details, container, false);

            ShowEvent activity = (ShowEvent) getActivity();
            myDataFromActivity = activity.returneid();

            Button bukbtn = (Button)view.findViewById(R.id.bookbtn);


            SharedPreferences sp = this.getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
            final String userid = sp.getString("userid","");
            final String usertype = sp.getString("usertype", "");

                final View customView = inflater.inflate(R.layout.my_custom_view, null);

                final View womenview = inflater.inflate(R.layout.women_custom_view, null);

                final View childview = inflater.inflate(R.layout.child_custom_view, null);

                final NumberPicker np = (NumberPicker)customView.findViewById(R.id.mennumberpicker);
                np.setMinValue(0);
                np.setMaxValue(20);

                final NumberPicker womenpicker = (NumberPicker)womenview.findViewById(R.id.womenpicker);
                womenpicker.setMinValue(0);
                womenpicker.setMaxValue(20);

                final NumberPicker childpicker = (NumberPicker)childview.findViewById(R.id.childpicker);
                childpicker.setMinValue(0);
                childpicker.setMaxValue(20);


            btnlayout = (Button)view.findViewById(R.id.btnEvent);

            bukbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    new BottomDialog.Builder(getActivity())
                            .setTitle("Men")
                            .setCustomView(customView)
                            .setPositiveText("Next")
                            .setPositiveBackgroundColorResource(R.color.khoogColor)
                            //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                            .setPositiveTextColorResource(android.R.color.white)
                            .onPositive(new BottomDialog.ButtonCallback() {
                                @Override
                                public void onClick(@NonNull BottomDialog bottomDialog) {

                                    bottomDialog.dismiss();

                                    men = np.getValue();

                                    new BottomDialog.Builder(getActivity())
                                            .setTitle("Women")
                                            .setCustomView(womenview)
                                            .setPositiveText("Next")
                                            .setPositiveBackgroundColorResource(R.color.khoogColor)
                                            //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                                            .setPositiveTextColorResource(android.R.color.white)
                                            .onPositive(new BottomDialog.ButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull BottomDialog bottomDialog) {

                                                    bottomDialog.dismiss();

                                                    women = womenpicker.getValue();


                                                    new BottomDialog.Builder(getActivity())
                                                            .setTitle("Children")
                                                            .setCustomView(womenview)
                                                            .setPositiveText("Next")
                                                            .setPositiveBackgroundColorResource(R.color.khoogColor)
                                                            //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                                                            .setPositiveTextColorResource(android.R.color.white)
                                                            .onPositive(new BottomDialog.ButtonCallback() {
                                                                @Override
                                                                public void onClick(@NonNull BottomDialog bottomDialog) {

                                                                    bottomDialog.dismiss();
                                                                    child = childpicker.getValue();


                                                                    Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                                                                    startActivity(intent);


                                                                }
                                                            })
                                                            .show();





                                                }
                                            })
                                            .show();






                                }
                            })
                            .show();


                }
            });

            Constants constants = new Constants();
            geturl = constants.getUrl();

            AndroidNetworking.post(geturl)
                    .addBodyParameter("action", "geteventinfobyandroid")
                    .addBodyParameter("eid", myDataFromActivity)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                String location = response.getString("loc");
                                TextView tv_loc = (TextView)view.findViewById(R.id.location);
                                tv_loc.setText(location);


                                String datestart = response.getString("start");
                                TextView startdate = (TextView)view.findViewById(R.id.eventdate);
                                startdate.setText(datestart);

                                String details = response.getString("details");
                                TextView tv_details = (TextView)view.findViewById(R.id.details);
                                tv_details.setText(details);

                                String rdays = response.getString("days");

                                String rper = response.getString("per");

                                String getrules = response.getString("rules");

                                String notes = response.getString("notes");

                                TextView tv_notes = (TextView)view.findViewById(R.id.specialnotes);

                                tv_notes.setText(notes);



                                String st = response.getString("st");

                                String compid = response.getString("compid");

                                LinearLayout l1 = (LinearLayout)view.findViewById(R.id.payoutlayout);

                                if (userid.equals(compid) && st.equals("2"))
                                {
                                    l1.setVisibility(View.VISIBLE);
                                }else{
                                    l1.setVisibility(View.GONE);
                                }

                                if (st.equals("0"))
                                {
                                    btnlayout.setText("Event Pending");
                                    btnlayout.setBackgroundColor(Color.parseColor("#EEEEEE"));
                                }

                                LinearLayout l2 = (LinearLayout)view.findViewById(R.id.btnlayout);
                                LinearLayout l3 = (LinearLayout)view.findViewById(R.id.booklayout);
                                if (usertype.equals("company"))
                                {
                                    l2.setVisibility(View.VISIBLE);
                                    l3.setVisibility(View.GONE);
                                }else{
                                    l2.setVisibility(View.GONE);
                                    l3.setVisibility(View.VISIBLE);
                                }




                                if (st.equals("1"))
                                {
                                    btnlayout.setText("Close Event");
                                }

                                if (st.equals("2"))
                                {
                                    btnlayout.setText("Event Closed");
                                    btnlayout.setBackgroundColor(Color.parseColor("#AAAAAA"));
                                }


                                TextView rules = (TextView)view.findViewById(R.id.rulestext);

                                if (getrules.equals(""))
                                {
                                    getrules = "No Rules And Regulation Added By Event Host";
                                }

                                rules.setText(getrules);

                                TextView refunddesp = (TextView)view.findViewById(R.id.refunddesp);

                                String refunddescriptiontext = "No refund if cancellation requested within "+rdays+" Days prior to the event.\n" +
                                        "Cancel before "+rdays+" days and get a "+rper+"% Refund";

                                refunddesp.setText(refunddescriptiontext);

                                TextView tv_cancel = (TextView)view.findViewById(R.id.getcancelleration);


                                String attraction = response.getString("att");

                                String acc = response.getString("acc");
                                String meal = response.getString("meal");

                                String refundpolicy = response.getString("refundPolicy");

                                tv_cancel.setText(refundpolicy);

                                LinearLayout meallayout = (LinearLayout)view.findViewById(R.id.meallayout);

                                if (meal.equals(""))
                                {
                                    meallayout.setVisibility(View.GONE);
                                }else{
                                    TextView tv_meal = (TextView)view.findViewById(R.id.mealdetails);
                                    tv_meal.setText(meal
                                    );
                                }

                                LinearLayout acclayout = (LinearLayout)view.findViewById(R.id.acclayout);

                                if (acc.equals(""))
                                {
                                    acclayout.setVisibility(View.GONE);
                                }else{
                                    TextView tv_acc = (TextView)view.findViewById(R.id.accdetails);
                                    tv_acc.setText(acc);
                                }

                                JSONArray jsonArray = new JSONArray(attraction);

                                LinearLayout attractionlayout = (LinearLayout)view.findViewById(R.id.attractionlayout);

                                LinearLayout whattodolayout = (LinearLayout)view.findViewById(R.id.whatttodo);

                                for (int i=0; i < jsonArray.length(); i++)
                                {
                                    String getattraction = jsonArray.getString(i);
                                    TextView tv_attraction = new TextView(getActivity());
                                    tv_attraction.setText(getattraction);
                                    tv_attraction.setTypeface(boldfont());
                                    attractionlayout.addView(tv_attraction);
                                }
                                String todoact = response.getString("act");
                                JSONArray jsonArray1 = new JSONArray(todoact);


                                for (int i=0; i<jsonArray1.length();i++)
                                {

                                    if (!jsonArray1.getString(i).equals(""))
                                    {

                                        LinearLayout linearLayout = new LinearLayout(getActivity());
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        params.setMargins(0, 15, 0,0);
                                        linearLayout.setLayoutParams(params);
                                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                                        TextView tv_todo = new TextView(getActivity());
                                        String gettext = jsonArray1.getString(i);
                                        tv_todo.setTypeface(boldfont());
                                        tv_todo.setText(gettext);

                                        LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        textparams.setMargins(10, 0, 0,0);
                                        tv_todo.setLayoutParams(textparams);

                                        ImageView imgView = new ImageView(getActivity());
                                        imgView.setImageResource(R.drawable.tck);
                                        int imageWidth = dpToPx(18);
                                        LinearLayout.LayoutParams imgparams = new LinearLayout.LayoutParams(imageWidth, imageWidth);
                                        imgView.setLayoutParams(imgparams);

                                        linearLayout.addView(imgView);
                                        linearLayout.addView(tv_todo);
                                        whattodolayout.addView(linearLayout);

                                    }


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


            btnlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String gettext = btnlayout.getText().toString();

                    if (gettext.equals("Close Event"))
                    {
                        closeEvent(inflater);
                    }



                }
            });


            return view;
    }


    public void closeEvent(LayoutInflater inflater)
    {
        final View customView = inflater.inflate(R.layout.close_event_view, null);

        final RadioGroup rggroup = (RadioGroup)customView.findViewById(R.id.radioGroup1);

        new BottomDialog.Builder(getActivity())
                .setTitle("Would you like to?\n ")
                .setCustomView(customView)
                .setPositiveText("Save")
                .setPositiveBackgroundColorResource(R.color.khoogColor)
                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog bottomDialog) {


                        int selectedid = rggroup.getCheckedRadioButtonId();

                        switch (selectedid)
                        {
                            case R.id.radio0:
                                closeyourevent();
                                break;

                            case R.id.radio1:
                                cancelevent();
                                break;

                            case R.id.radio2:
                                rescheduleevent();
                                break;
                        }

                    }
                })
                .show();

    }

    public void closeyourevent()
    {
        AndroidNetworking.post(geturl)
                .addBodyParameter("action", "eventClosed")
                .addBodyParameter("event_id", myDataFromActivity)
                .addBodyParameter("eventStatus", "2")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            String getresp = response.getString("result");

                            if (getresp.equals("success"))
                            {
                                Toast.makeText(getActivity().getApplicationContext(), "Event Closed Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }
    public void cancelevent()
    {

    }
    public void rescheduleevent(){

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public Typeface boldfont()
    {
        Typeface robotobold = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/robotobold.ttf");

        return robotobold;
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
