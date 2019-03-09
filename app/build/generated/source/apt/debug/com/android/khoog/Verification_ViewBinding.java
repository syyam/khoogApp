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

public class Verification_ViewBinding implements Unbinder {
  private Verification target;

  @UiThread
  public Verification_ViewBinding(Verification target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Verification_ViewBinding(Verification target, View source) {
    this.target = target;

    target.title = Utils.findRequiredViewAsType(source, R.id.mainTitle, "field 'title'", TextView.class);
    target.subtxt = Utils.findRequiredViewAsType(source, R.id.subtext, "field 'subtxt'", TextView.class);
    target.avVerify = Utils.findRequiredViewAsType(source, R.id.aviVerify, "field 'avVerify'", AVLoadingIndicatorView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Verification target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.title = null;
    target.subtxt = null;
    target.avVerify = null;
  }
}
