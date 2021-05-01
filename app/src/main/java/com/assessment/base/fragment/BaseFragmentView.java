package com.assessment.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import com.assessment.base.BaseView;

public interface BaseFragmentView {

    interface View<P extends BaseFragmentPresenterImpl> extends BaseView<P> {

        Bundle getFragmentArguments();
        void setupUI();
        int getToolbarTitle();
        void switchNestedFragment(final BaseFragment _fragment, @IdRes int _idContainer, boolean _addToBackStack);
    }

    abstract class BaseFragmentPresenterImpl<V extends View, R extends BaseFragmentRouter> implements BaseFragmentPresenter<V, R> {

        protected V view;
        protected R router;

        @Override
        public void bindView(V view) {
            this.view = view;
        }

        @Override
        public void onDestroyView() {}

        @Override
        public void unBindRouter() {
            router = null;
        }

        @Override
        public void unBindView() {
            view = null;
        }

        @Override
        public void onViewCreated(Bundle _bundle) {}

        @Override
        public void onSaveState(Bundle bundle) {}

        @Override
        public void bindRouter(R router) {
            this.router = router;
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
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        }
    }
}
