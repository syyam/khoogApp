// Generated code from Butter Knife. Do not modify!
package com.android.khoog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.layout_mail = Utils.findRequiredViewAsType(source, R.id.layout1, "field 'layout_mail'", LinearLayout.class);
    target.et_email = Utils.findRequiredViewAsType(source, R.id.email, "field 'et_email'", EditText.class);
    target.et_pass = Utils.findRequiredViewAsType(source, R.id.password, "field 'et_pass'", EditText.class);
    target.layout_pass = Utils.findRequiredViewAsType(source, R.id.layout2, "field 'layout_pass'", LinearLayout.class);
    target.loginbtn = Utils.findRequiredViewAsType(source, R.id.login, "field 'loginbtn'", Button.class);
    target.tv_reg = Utils.findRequiredViewAsType(source, R.id.reg, "field 'tv_reg'", TextView.class);
    target.partnerText = Utils.findRequiredViewAsType(source, R.id.partner, "field 'partnerText'", TextView.class);
    target.uppertext = Utils.findRequiredViewAsType(source, R.id.registerUperText, "field 'uppertext'", TextView.class);
    target.bptext = Utils.findRequiredViewAsType(source, R.id.becomePartner, "field 'bptext'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.layout_mail = null;
    target.et_email = null;
    target.et_pass = null;
    target.layout_pass = null;
    target.loginbtn = null;
    target.tv_reg = null;
    target.partnerText = null;
    target.uppertext = null;
    target.bptext = null;
  }
}
