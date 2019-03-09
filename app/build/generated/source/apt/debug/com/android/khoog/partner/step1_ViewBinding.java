// Generated code from Butter Knife. Do not modify!
package com.android.khoog.partner;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.khoog.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class step1_ViewBinding implements Unbinder {
  private step1 target;

  @UiThread
  public step1_ViewBinding(step1 target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public step1_ViewBinding(step1 target, View source) {
    this.target = target;

    target.welcome = Utils.findRequiredViewAsType(source, R.id.welcome, "field 'welcome'", TextView.class);
    target.howOperate = Utils.findRequiredViewAsType(source, R.id.howOperate, "field 'howOperate'", TextView.class);
    target.getStarted = Utils.findRequiredViewAsType(source, R.id.getStarted, "field 'getStarted'", TextView.class);
    target.compfont = Utils.findRequiredViewAsType(source, R.id.companyFont, "field 'compfont'", TextView.class);
    target.indifont = Utils.findRequiredViewAsType(source, R.id.indifont, "field 'indifont'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    step1 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.welcome = null;
    target.howOperate = null;
    target.getStarted = null;
    target.compfont = null;
    target.indifont = null;
  }
}
