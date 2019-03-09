// Generated code from Butter Knife. Do not modify!
package com.android.khoog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GetStarted_ViewBinding implements Unbinder {
  private GetStarted target;

  @UiThread
  public GetStarted_ViewBinding(GetStarted target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GetStarted_ViewBinding(GetStarted target, View source) {
    this.target = target;

    target.startedheading = Utils.findRequiredViewAsType(source, R.id.getStartedText, "field 'startedheading'", TextView.class);
    target.alreadyAccount = Utils.findRequiredViewAsType(source, R.id.alreadyAccount, "field 'alreadyAccount'", TextView.class);
    target.avloader = Utils.findRequiredViewAsType(source, R.id.aviGetStarted, "field 'avloader'", AVLoadingIndicatorView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GetStarted target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.startedheading = null;
    target.alreadyAccount = null;
    target.avloader = null;
  }
}
