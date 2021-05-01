package com.assessment.base.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.assessment.base.BaseView;

public interface BaseActivityView {

    interface View<P extends Presenter> extends BaseView<P> {

        void setupToolbar();

        void setupUI();

        void setToolbarTitle(@StringRes int textRes);

        void readBundle(Bundle bundle);

        Intent getIntent();
    }

    abstract class Presenter<V extends View, R extends BaseActivityRouter> implements BaseActivityPresenter<V, R> {

        protected V view;
        protected R router;

        @Override
        @CallSuper
        public void bindView(V view) {
            this.view = view;
        }

        @Override
        @CallSuper
        public void bindRouter(R router) {
            this.router = router;
        }

        @Override
        public void onViewCreated(@Nullable Bundle _savedInstanceState) {
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        }

        @Override
        public void onStart() {
        }

        @Override
        public void onResume() {
        }

        @Override
        public void onPause() {
        }

        @Override
        public void onStop() {
        }

        @Override
        public void onDestroyView() {
        }

        @Override
        public void unBindRouter() {
            router = null;
        }

        @Override
        public void unBindView() {
            view = null;
        }

        public void onSaveData(Bundle _savedInstanceState) {
        }

        public void onRestoreData(Bundle _savedInstanceState) {
        }
    }
}
