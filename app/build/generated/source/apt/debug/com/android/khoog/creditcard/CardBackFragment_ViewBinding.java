// Generated code from Butter Knife. Do not modify!
package com.android.khoog.creditcard;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.khoog.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CardBackFragment_ViewBinding implements Unbinder {
  private CardBackFragment target;

  @UiThread
  public CardBackFragment_ViewBinding(CardBackFragment target, View source) {
    this.target = target;

    target.tv_cvv = Utils.findRequiredViewAsType(source, R.id.tv_cvv, "field 'tv_cvv'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CardBackFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_cvv = null;
  }
}
