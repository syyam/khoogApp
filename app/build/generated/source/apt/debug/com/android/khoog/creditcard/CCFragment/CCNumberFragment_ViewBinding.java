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

public class CCNumberFragment_ViewBinding implements Unbinder {
  private CCNumberFragment target;

  @UiThread
  public CCNumberFragment_ViewBinding(CCNumberFragment target, View source) {
    this.target = target;

    target.et_number = Utils.findRequiredViewAsType(source, R.id.et_number, "field 'et_number'", CreditCardEditText.class);
    target.et_name = Utils.findRequiredViewAsType(source, R.id.et_name, "field 'et_name'", CreditCardEditText.class);
    target.et_validity = Utils.findRequiredViewAsType(source, R.id.et_validity, "field 'et_validity'", CreditCardEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CCNumberFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.et_number = null;
    target.et_name = null;
    target.et_validity = null;
  }
}
