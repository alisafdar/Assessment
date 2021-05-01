package com.assessment.base.activity;

import android.content.Intent;
import androidx.annotation.Nullable;
import com.assessment.base.BasePresenter;

public interface BaseActivityPresenter<V extends BaseActivityView.View, R extends BaseActivityRouter> extends BasePresenter<V, R> {

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
}
