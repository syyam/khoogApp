// Generated code from Butter Knife. Do not modify!
package com.android.khoog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wang.avi.AVLoadingIndicatorView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterUser_ViewBinding implements Unbinder {
  private RegisterUser target;

  @UiThread
  public RegisterUser_ViewBinding(RegisterUser target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterUser_ViewBinding(RegisterUser target, View source) {
    this.target = target;

    target.back = Utils.findRequiredViewAsType(source, R.id.backarrow, "field 'back'", ImageView.class);
    target.et_name = Utils.findRequiredViewAsType(source, R.id.fullname, "field 'et_name'", EditText.class);
    target.et_email = Utils.findRequiredViewAsType(source, R.id.emailid, "field 'et_email'", EditText.class);
    target.et_phone = Utils.findRequiredViewAsType(source, R.id.phone, "field 'et_phone'", EditText.class);
    target.et_password = Utils.findRequiredViewAsType(source, R.id.regpass, "field 'et_password'", EditText.class);
    target.sign = Utils.findRequiredViewAsType(source, R.id.signin, "field 'sign'", TextView.class);
    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.radio_grop, "field 'radioGroup'", RadioGroup.class);
    target.checkBox = Utils.findRequiredViewAsType(source, R.id.terms_check, "field 'checkBox'", CheckBox.class);
    target.regBtn = Utils.findRequiredViewAsType(source, R.id.regBtn, "field 'regBtn'", Button.class);
    target.tvCreateAccount = Utils.findRequiredViewAsType(source, R.id.createAccountText, "field 'tvCreateAccount'", TextView.class);
    target.genderfont = Utils.findRequiredViewAsType(source, R.id.genderfont, "field 'genderfont'", TextView.class);
    target.rbfemale = Utils.findRequiredViewAsType(source, R.id.femaleRadio, "field 'rbfemale'", RadioButton.class);
    target.rbmale = Utils.findRequiredViewAsType(source, R.id.maleRadio, "field 'rbmale'", RadioButton.class);
    target.alreadyaccount = Utils.findRequiredViewAsType(source, R.id.alreadyaccount, "field 'alreadyaccount'", TextView.class);
    target.loader = Utils.findRequiredViewAsType(source, R.id.avi, "field 'loader'", AVLoadingIndicatorView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterUser target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.back = null;
    target.et_name = null;
    target.et_email = null;
    target.et_phone = null;
    target.et_password = null;
    target.sign = null;
    target.radioGroup = null;
    target.checkBox = null;
    target.regBtn = null;
    target.tvCreateAccount = null;
    target.genderfont = null;
    target.rbfemale = null;
    target.rbmale = null;
    target.alreadyaccount = null;
    target.loader = null;
  }
}
