package com.assessment.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

public interface BasePresenter<V extends BaseView, R extends BaseRouter> {

    void onViewCreated(@Nullable Bundle _savedInstanceState);

    void onDestroyView();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void bindView(V view);

    void unBindView();

    void bindRouter(R router);

    void unBindRouter();
}
