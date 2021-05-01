package com.assessment.ui.details

import android.os.Bundle
import com.assessment.constants.INTENT_EXTRA_NEWS_URL
import javax.inject.Inject

class DetailsPresenter @Inject constructor() : DetailsContract.Presenter() {
    override fun onViewCreated(_savedInstanceState: Bundle?) {
        super.onViewCreated(_savedInstanceState)
        populateData()
    }

    private fun populateData() {
        val url = view.intent.getStringExtra(INTENT_EXTRA_NEWS_URL) as String
        view.loadWebView(url)
    }
}