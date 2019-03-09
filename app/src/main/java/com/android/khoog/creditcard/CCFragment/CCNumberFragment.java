package com.android.khoog.creditcard.CCFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.khoog.R;
import com.android.khoog.creditcard.CardFrontFragment;
import com.android.khoog.creditcard.CheckOutActivity;
import com.android.khoog.creditcard.Utils.CreditCardEditText;
import com.android.khoog.creditcard.Utils.CreditCardExpiryTextWatcher;
import com.android.khoog.creditcard.Utils.CreditCardFormattingTextWatcher;
import butterknife.BindView;
import butterknife.ButterKnife;
public class CCNumberFragment extends Fragment {
    @BindView(R.id.et_number)
    CreditCardEditText et_number;
    TextView tv_number;
    @BindView(R.id.et_name)
    CreditCardEditText et_name;
    TextView tv_Name;
    @BindView(R.id.et_validity)
    CreditCardEditText et_validity;
    TextView tv_validity;
    CheckOutActivity activity;
    CardFrontFragment cardFrontFragment;
    public CCNumberFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ccnumber, container, false);
        ButterKnife.bind(this, view);
        activity = (CheckOutActivity) getActivity();
        cardFrontFragment = activity.cardFrontFragment;
        tv_number = cardFrontFragment.getNumber();
        tv_Name = cardFrontFragment.getName();
        tv_validity = cardFrontFragment.getValidity();
        //Do your stuff
        et_number.addTextChangedListener(new CreditCardFormattingTextWatcher(et_number, tv_number,cardFrontFragment.getCardType(),new CreditCardFormattingTextWatcher.CreditCardType() {
            @Override
            public void setCardType(int type) {
                Log.d("Card", "setCardType: "+type);

                cardFrontFragment.setCardType(type);
            }
        }));
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if(tv_Name!=null)
                {
                    if (TextUtils.isEmpty(editable.toString().trim()))
                        tv_Name.setText("CARD HOLDER");
                    else
                        tv_Name.setText(editable.toString());
                }
            }
        });
        et_name.setOnBackButtonListener(new CreditCardEditText.BackButtonListener() {
            @Override
            public void onBackClick() {
                if(activity!=null)
                    activity.onBackPressed();
            }
        });
        et_validity.addTextChangedListener(new CreditCardExpiryTextWatcher(et_validity, tv_validity));
        et_validity.setOnBackButtonListener(new CreditCardEditText.BackButtonListener() {
            @Override
            public void onBackClick() {
                if(activity!=null)
                    activity.onBackPressed();
            }
        });
        return view;
    }
    public String getCardNumber()
    {
        if(et_number!=null)
            return et_number.getText().toString().trim();
        return null;
    }
    public String getName()
    {
        if(et_name!=null)
            return et_name.getText().toString().trim();
        return null;
    }
    public String getValidity()
    {
        if(et_validity!=null)
            return et_validity.getText().toString().trim();
        return null;
    }
}