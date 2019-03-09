package com.android.khoog.creditcard.CCFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.khoog.R;
import com.android.khoog.creditcard.CheckOutActivity;
import com.android.khoog.creditcard.Utils.CreditCardEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import static android.content.ContentValues.TAG;
public class CCSecureCodeFragment extends Fragment {
    @BindView(R.id.et_cvv)
    CreditCardEditText et_cvv;
    TextView tv_cvv;
    CheckOutActivity activity;
    public CCSecureCodeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ccsecure_code, container, false);
        ButterKnife.bind(this, view);
        activity = (CheckOutActivity) getActivity();
        et_cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (tv_cvv != null) {
                    if (TextUtils.isEmpty(editable.toString().trim()))
                        tv_cvv.setText("XXX");
                    else
                        tv_cvv.setText(editable.toString());
                } else
                    Log.d(TAG, "afterTextChanged: cvv null");
            }
        });
        et_cvv.setOnBackButtonListener(new CreditCardEditText.BackButtonListener() {
            @Override
            public void onBackClick() {
                if(activity!=null)
                    activity.onBackPressed();
            }
        });
        return view;
    }
    public void setCvv(TextView tv) {
        tv_cvv = tv;
    }
    public String getValue() {
        String getValue = "";
        if (et_cvv != null) {
            getValue = et_cvv.getText().toString().trim();
        }
        return getValue;
    }
}