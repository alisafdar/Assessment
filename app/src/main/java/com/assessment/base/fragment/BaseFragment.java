package com.assessment.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.assessment.R;
import com.assessment.base.IHasInterface;
import com.assessment.base.activity.BaseActivity;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public abstract class BaseFragment<P extends BaseFragmentView.BaseFragmentPresenterImpl> extends Fragment
        implements BaseFragmentView.View<P>, BaseFragmentRouter {

    @Inject
    protected P presenter;
    private View rootView;
    private BaseActivity activity;
    private Bundle presenterSate;
    private static final String KEY_PRESENTER_STATE = "PRESENTER_STATE";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        presenter = createPresenter();
        if (context instanceof BaseActivity) activity = ((BaseActivity) context);
        else
            new ClassCastException("Activity should extend BaseActivity " + this.getClass().getName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutRes(), container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.bindView(this);
        presenter.bindRouter(this);
        setupUI();
        if (savedInstanceState != null) {
            presenterSate = savedInstanceState.getBundle(KEY_PRESENTER_STATE);
        }
        presenter.onViewCreated(presenterSate);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenterSate = new Bundle();
            presenter.onSaveState(presenterSate);
            outState.putBundle(KEY_PRESENTER_STATE, presenterSate);
        }
    }

    @Override
    public void onDestroyView() {
        presenterSate = new Bundle();
        presenter.onSaveState(presenterSate);
        presenter.onDestroyView();

        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        if(presenter!=null){
//        presenter.unBindRouter();
//        presenter.unBindView();
//        presenter = null;
// }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void showToast(String message) {
        activity.showToast(message);
    }

    @Override
    public void popBackStack() {
        getFragmentManager().popBackStackImmediate();
    }

    @Override
    public void switchNestedFragment(@NonNull BaseFragment _fragment, @IdRes int _idContainer, boolean _addToBackStack) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager()
                .beginTransaction();

        if (_addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        fragmentTransaction.replace(_idContainer, _fragment, _fragment.getClass().getName());
        try { fragmentTransaction.commit(); } finally { }
    }

    @Nullable
    @Override
    public <T extends BaseFragment<?>> T findNestedFragmentById(@IdRes int _idContainer) {
        try {
            return findNestedFragmentByIdOrThrow(_idContainer);
        } catch (Exception _e) {
            return null;
        }
    }

    @Nullable
    public <T extends BaseFragment> T findNestedFragmentByIdOrThrow(@IdRes int _idContainer) throws ClassCastException {
        return (T) getChildFragmentManager().findFragmentById(_idContainer);
    }

    @Override
    public String getStringValue(@StringRes int id) {
        return getString(id);
    }

    @Override
    public void showProgress() {
        activity.showProgress();
    }

    @Override
    public void hideProgress() {
        activity.hideProgress();
    }

    @Override
    public void showKeyboard() {
        activity.showKeyboard();
    }

    @Override
    public void hideKeyboard() {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void clickBack() {
        activity.clickBack();
    }

    @Override
    public void finishActivity() {
        activity.finishActivity();
    }

    @Override
    public void finishActivityResult(int requestCode) {
        activity.finishActivityResult(requestCode);
    }

    @Override
    public void finishActivityResult(int requestCode, Bundle bundle) {
        activity.finishActivityResult(requestCode, bundle);
    }

    @Override
    public void replaceFragment(@NotNull BaseFragment fragment, boolean addToBackStack) {
        activity.replaceFragment(fragment, addToBackStack);
    }

    @Override
    public void startActivity(@NotNull Class _activityClass, @Nullable Bundle _bundle, boolean enableAnim) {
        activity.startActivity(_activityClass, _bundle, enableAnim);
    }

    @Override
    public void startActivity(@NotNull Class _activityClass, @NotNull int... _flag) {
        activity.startActivity(_activityClass, _flag);
    }

    @Override
    public void startActivityForResult(@NotNull Class<?> _activityClass, int requestCode, @Nullable Bundle _bundle) {
        Intent intent = new Intent(getActivity(), _activityClass);
        if (_bundle != null) intent.putExtras(_bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasInterface<T>) getActivity()).getComponent());
    }

    @Override
    public void noInternet() {
        Toast.makeText(getViewContext(), getString(R.string.internet_available), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isConnected() {
        return activity.isConnected;
    }

    @Override
    public Bundle getFragmentArguments() {
        return getArguments();
    }
}
