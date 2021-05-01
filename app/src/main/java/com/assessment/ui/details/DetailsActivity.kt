package com.assessment.ui.details

import android.os.Bundle
import com.assessment.R
import com.assessment.base.activity.BaseActivity
import com.assessment.base.fragment.BaseFragment
import com.assessment.constants.INTENT_EXTRA_NEWS_TITLE
import com.assessment.constants.INTENT_EXTRA_NEWS_URL
import kotlinx.android.synthetic.main.activity_details.*
import com.assessment.di.AppComponent

class DetailsActivity :BaseActivity<DetailsContract.Presenter>(), DetailsContract.View {

    override fun getLayoutRes(): Int = R.layout.activity_details

    override fun setupUI() {
        setupToolbar()
    }

    override fun setupToolbar() {
        super.setupToolbar()
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
            it.title = intent.getStringExtra(INTENT_EXTRA_NEWS_TITLE)
        }
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerDetailsComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun loadWebView(url: String) {
        webView.loadUrl(url)
    }

    companion object {
        fun getBundle(url: String?, title: String?): Bundle {
            val bundle: Bundle = Bundle()
            bundle.putString(INTENT_EXTRA_NEWS_URL, url)
            bundle.putString(INTENT_EXTRA_NEWS_TITLE, title)
            return bundle
        }
    }

    override fun replaceFragment(fragment: BaseFragment<*>, addToBackStack: Boolean) {
    }
}