// Generated code from Butter Knife. Do not modify!
package com.android.khoog.creditcard;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.khoog.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CardFrontFragment_ViewBinding implements Unbinder {
  private CardFrontFragment target;

  @UiThread
  public CardFrontFragment_ViewBinding(CardFrontFragment target, View source) {
    this.target = target;

    target.tvNumber = Utils.findRequiredViewAsType(source, R.id.tv_card_number, "field 'tvNumber'", TextView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_member_name, "field 'tvName'", TextView.class);
    target.tvValidity = Utils.findRequiredViewAsType(source, R.id.tv_validity, "field 'tvValidity'", TextView.class);
    target.ivType = Utils.findRequiredViewAsType(source, R.id.ivType, "field 'ivType'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CardFrontFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvNumber = null;
    target.tvName = null;
    target.tvValidity = null;
    target.ivType = null;
  }
}
