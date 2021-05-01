package com.assessment.base;

import android.content.Context;
import androidx.annotation.CheckResult;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

public interface BaseView<P extends BasePresenter> {

    Context getViewContext();

    P getPresenter();

    @LayoutRes
    int getLayoutRes();

    void showToast(String message);

    @CheckResult
    String getStringValue(@StringRes int id);

   boolean isConnected();
   void noInternet();
}
