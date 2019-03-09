// Generated code from Butter Knife. Do not modify!
package com.android.khoog.creditcard.CCFragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.khoog.R;
import com.android.khoog.creditcard.Utils.CreditCardEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CCSecureCodeFragment_ViewBinding implements Unbinder {
  private CCSecureCodeFragment target;

  @UiThread
  public CCSecureCodeFragment_ViewBinding(CCSecureCodeFragment target, View source) {
    this.target = target;

    target.et_cvv = Utils.findRequiredViewAsType(source, R.id.et_cvv, "field 'et_cvv'", CreditCardEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CCSecureCodeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.et_cvv = null;
  }
}
