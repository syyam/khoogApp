package com.android.khoog.creditcard;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.khoog.R;
import com.android.khoog.creditcard.CCFragment.CCSecureCodeFragment;
import com.android.khoog.creditcard.Utils.FontTypeChange;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CardBackFragment extends Fragment {
    @BindView(R.id.tv_cvv)TextView tv_cvv;
    FontTypeChange fontTypeChange;
 CheckOutActivity activity;
    CCSecureCodeFragment securecode;

    public CardBackFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_card_back, container, false);
        ButterKnife.bind(this, view);
        activity = (CheckOutActivity) getActivity();
        securecode = activity.secureCodeFragment;
        securecode.setCvv(tv_cvv);
        if(!TextUtils.isEmpty(securecode.getValue()))
            tv_cvv.setText(securecode.getValue());
        return view;
    }
}