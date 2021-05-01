package com.assessment.base.activity;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.assessment.R;
import com.assessment.base.App;
import com.assessment.base.fragment.BaseFragment;
import com.assessment.base.fragment.BaseFragmentPresenter;
import com.assessment.base.fragment.BaseFragmentView;
import com.assessment.constants.AppConstantsKt;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import com.assessment.di.AppComponent;
import org.jetbrains.annotations.NotNull;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;


public abstract class BaseActivity<P extends BaseActivityView.Presenter> extends AppCompatActivity implements BaseActivityView.View<P>, BaseActivityRouter
{
    private Subscription internetConnectivitySubscription;

    @Inject
    protected P presenter;
//    protected Toolbar toolbar;
    //    protected TextView toolbarTitle;
    private ProgressDialog progressDialog;
    public Boolean isConnected = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setupComponent(App.component);
        if (getLayoutRes() != 0)
            setContentView(getLayoutRes());
        presenter.bindView(this);
        presenter.bindRouter(this);

        setupUI();

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment fragment = getSupportFragmentManager().findFragmentById(getFragmentContainer());
            if (fragment instanceof BaseFragment)
                setToolbarTitle(((BaseFragment) fragment).getToolbarTitle());
        });
        presenter.onViewCreated(savedInstanceState);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void readBundle(Bundle bundle)
    {
    }

    @Override
    protected void onStop()
    {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed()
    {
        hideKeyboard();
        super.onBackPressed();
    }

    @Override
    public Context getViewContext()
    {
        return this;
    }

    @Override
    protected void onDestroy()
    {
        if (presenter != null)
        {
            presenter.unBindRouter();
            presenter.unBindView();
            presenter.onDestroyView();
        }
        super.onDestroy();
    }

    @Override
    public void showProgress()
    {
        if (progressDialog == null || !progressDialog.isShowing())
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @IdRes
    public int getFragmentContainer()
    {
        return 0;
    }

    @Override
    public void hideProgress()
    {
        try
        {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }
        catch (IllegalStateException e)
        {
            Log.e(AppConstantsKt.TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public void clickBack()
    {
        onBackPressed();
    }

    @Override
    public void finishActivity()
    {
        finish();
    }

    @Override
    public void finishActivityResult(int requestCode)
    {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void finishActivityResult(int requestCode, Bundle bundle)
    {
        Intent intent = new Intent();
        intent.putExtra("bundle", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
        {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    @Override
    public void hideKeyboard()
    {
        final android.view.View focusedView = getCurrentFocus();
        if (focusedView != null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    @Override
    public void startActivityForResult(@NotNull Class<?> _activityClass, int requestCode, @Nullable Bundle _bundle)
    {
        Intent intent = new Intent(this, _activityClass);
        if (_bundle != null)
            intent.putExtras(_bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivity(@NotNull Class<?> _activityClass, @Nullable Bundle _bundle, boolean enableAnim)
    {
        Intent intent = new Intent(this, _activityClass);
        if (!enableAnim)
        {
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }
        if (_bundle != null)
        {
            intent.putExtras(_bundle);
        }
        startActivity(intent);
    }

    @Override
    public void startActivity(@NotNull Class<?> _activityClass, @NotNull int... _flag)
    {
        Intent intent = new Intent(this, _activityClass);
        for (int a_flag : _flag)
        {
            intent.setFlags(a_flag);
        }
        startActivity(intent);
    }

    @Override
    public void setupToolbar()
    {
//        toolbar = findView(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null)
//            getSupportActionBar().setDisplayShowTitleEnabled(true);
    }


    @Override
    public void setToolbarTitle(@StringRes int textRes)
    {
        getSupportActionBar().setTitle(textRes);
    }

    protected final <T extends android.view.View> T findView(@IdRes int id)
    {
        return (T) findViewById(id);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
        final BaseFragmentView.View fragmentView = (BaseFragmentView.View) getSupportFragmentManager().findFragmentById(getFragmentContainer());
        if (fragmentView != null)
        {
            ((BaseFragmentPresenter) fragmentView.getPresenter()).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public P getPresenter()
    {
        return presenter;
    }

    @Override
    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected abstract void setupComponent(AppComponent appComponent);

    @Override
    public String getStringValue(@StringRes int id)
    {
        return getString(id);
    }

    @Override
    public boolean isConnected()
    {
        return isConnected;
    }

    @Override
    public void noInternet()
    {
        Toast.makeText(getViewContext(), getString(R.string.internet_available), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        internetConnectivitySubscription =
                ReactiveNetwork.observeInternetConnectivity(3000, 10000, "google.com", 80, 5000).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(isConnectedToInternet -> {
            isConnected = isConnectedToInternet;
        }, throwable -> {
        });
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        safelyUnsubscribe(internetConnectivitySubscription);
    }

    private void safelyUnsubscribe(Subscription... subscriptions)
    {
        for (Subscription subscription : subscriptions)
        {
            if (subscription != null && !subscription.isUnsubscribed())
            {
                subscription.unsubscribe();
            }
        }
    }

    private boolean isAppOnForeground(Context context)
    {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
        {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses)
        {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        presenter.onSaveData(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.onRestoreData(savedInstanceState);
    }
}
