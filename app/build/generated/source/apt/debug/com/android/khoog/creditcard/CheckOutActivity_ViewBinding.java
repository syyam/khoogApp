// Generated code from Butter Knife. Do not modify!
package com.android.khoog.creditcard;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.khoog.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CheckOutActivity_ViewBinding implements Unbinder {
  private CheckOutActivity target;

  @UiThread
  public CheckOutActivity_ViewBinding(CheckOutActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CheckOutActivity_ViewBinding(CheckOutActivity target, View source) {
    this.target = target;

    target.btnNext = Utils.findRequiredViewAsType(source, R.id.btnNext, "field 'btnNext'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CheckOutActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnNext = null;
  }
}
