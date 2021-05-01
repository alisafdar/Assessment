package com.assessment.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.assessment.base.BasePresenter;
import com.assessment.base.BaseView;

public interface BaseFragmentPresenter<V extends BaseView, R extends BaseFragmentRouter> extends BasePresenter<V, R> {

    void onSaveState(Bundle bundle);
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

}
