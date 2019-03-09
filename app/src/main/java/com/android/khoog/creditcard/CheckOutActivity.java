package com.android.khoog.creditcard;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import com.android.khoog.R;
import com.android.khoog.creditcard.CCFragment.CCNumberFragment;
import com.android.khoog.creditcard.CCFragment.CCSecureCodeFragment;
import com.android.khoog.creditcard.Utils.CreditCardUtils;
import com.android.khoog.creditcard.Utils.ViewPagerAdapter;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import butterknife.BindView;
import butterknife.ButterKnife;
public class CheckOutActivity extends FragmentActivity implements FragmentManager.OnBackStackChangedListener {
    @BindView(R.id.btnNext)
    Button btnNext;

    public CardFrontFragment cardFrontFragment;
    public CardBackFragment cardBackFragment;
    //This is our viewPager
    private ViewPager viewPager;
    CCNumberFragment numberFragment;
    CCSecureCodeFragment secureCodeFragment;
    int total_item;
    boolean backTrack = false;
    private boolean mShowingBack = false;
    String cardNumber, cardCVV, cardValidity, cardName;
    Card card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final String finalstring = intent.getStringExtra("total_strng");
        final String finalstring1 = intent.getStringExtra("total_strng1");
        final String finalstring2 = intent.getStringExtra("total_strng2");
        final String radio_final = intent.getStringExtra("total_radio");
        final String radio_final1 = intent.getStringExtra("total_radio1");
        final String radio_final2 = intent.getStringExtra("total_radio2");
        final String finalcb  = intent.getStringExtra("cb");
        Toast.makeText(this, finalcb, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, finalstring+"\n"+finalstring1+"\n"+finalstring2+"\n"+radio_final+"\n"+radio_final1+"\n"+radio_final2, Toast.LENGTH_SHORT).show();
        cardFrontFragment = new CardFrontFragment();
        cardBackFragment = new CardBackFragment();
        if (savedInstanceState == null) {
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, cardFrontFragment).commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }
        getFragmentManager().addOnBackStackChangedListener(this);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == total_item)
                    btnNext.setText("Pay Now");
                else
                    btnNext.setText("Next");
                Log.d("track", "onPageSelected: " + position);
                if (position == total_item) {
                    flipCard();
                    backTrack = true;
                } else if (position == total_item - 1 && backTrack) {
                    flipCard();
                    backTrack = false;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewPager.getCurrentItem();
                if (pos < total_item) {
                    viewPager.setCurrentItem(pos + 1);
                } else {
                   // checkEntries();
                    cardName = numberFragment.getName();
                    cardNumber = numberFragment.getCardNumber();
                    cardValidity = numberFragment.getValidity();
                    cardCVV = secureCodeFragment.getValue();

                    if (TextUtils.isEmpty(cardName)) {
                        Toast.makeText(CheckOutActivity.this, "Enter Valid Name", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(cardNumber) || !CreditCardUtils.isValid(cardNumber.replace(" ",""))) {
                        Toast.makeText(CheckOutActivity.this, "Enter Valid card number", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(cardValidity)||!CreditCardUtils.isValidDate(cardValidity)) {
                        Toast.makeText(CheckOutActivity.this, "Enter correct validity", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(cardCVV)||cardCVV.length()<3) {
                        Toast.makeText(CheckOutActivity.this, "Enter valid security number", Toast.LENGTH_SHORT).show();
                    } else
                        {
                        Stripe stripe = new Stripe(getApplicationContext(), "pk_test_VfUf7GZglhaBX8w288O7Gm53");
                        stripe.createToken(
                                new Card(cardNumber, 12, 2019, cardCVV),
                                new TokenCallback() {
                                    public void onSuccess(Token token) {
                                        // Send token to your server
                                        Toast.makeText(CheckOutActivity.this, "your card is add"+"\n"+token.getId(), Toast.LENGTH_SHORT).show();

                                    }

                                    public void onError(Exception error) {
                                        // Show localized error message
                                        Toast.makeText(CheckOutActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                    }  }
            }
        });
    }
   /* public void checkEntries() {
        cardName = numberFragment.getName();
        cardNumber = numberFragment.getCardNumber();
        cardValidity = numberFragment.getValidity();
        cardCVV = secureCodeFragment.getValue();



       if (TextUtils.isEmpty(cardName)) {
            Toast.makeText(CheckOutActivity.this, "Enter Valid Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardNumber) || !CreditCardUtils.isValid(cardNumber.replace(" ",""))) {
            Toast.makeText(CheckOutActivity.this, "Enter Valid card number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardValidity)||!CreditCardUtils.isValidDate(cardValidity)) {
            Toast.makeText(CheckOutActivity.this, "Enter correct validity", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardCVV)||cardCVV.length()<3) {
            Toast.makeText(CheckOutActivity.this, "Enter valid security number", Toast.LENGTH_SHORT).show();
        } else

       card =  new Card(cardNumber,12,2019,cardCVV);
            Toast.makeText(CheckOutActivity.this, "Your card is added", Toast.LENGTH_SHORT).show();
    }*/
    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        numberFragment = new CCNumberFragment();
        secureCodeFragment = new CCSecureCodeFragment();
        adapter.addFragment(numberFragment);
        adapter.addFragment(secureCodeFragment);

        total_item = adapter.getCount() - 1;
        viewPager.setAdapter(adapter);
    }
    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        mShowingBack = true;
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.fragment_container,cardBackFragment)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void onBackPressed() {
        int pos = viewPager.getCurrentItem();
        if (pos > 0) {
            viewPager.setCurrentItem(pos - 1);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }
}