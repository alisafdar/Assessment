package com.assessment.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import com.assessment.R
import com.assessment.constants.BUNDLE_NEWS_MODEL
import com.assessment.data.DisposableManager
import com.assessment.data.SchedulerProvider
import com.assessment.data.models.response.NewsResponse
import com.assessment.data.models.response.NewsResults
import com.assessment.ui.details.DetailsActivity
import com.assessment.ui.main.adapter.MainAdapter
import com.assessment.data.interactors.NewsInteractor
import com.assessment.constants.ALL_SECTIONS
import com.assessment.constants.PERIOD
import javax.inject.Inject

class MainPresenter @Inject constructor(private val interactor: NewsInteractor, private val schedulerProvider: SchedulerProvider) : MainContract.Presenter() {

    private val disposableManager: DisposableManager by lazy { DisposableManager() }
    lateinit var newsResponse: NewsResponse
    private val adapterNews: MainAdapter by lazy { MainAdapter() }

    override fun onViewCreated(_savedInstanceState: Bundle?) {
        super.onViewCreated(_savedInstanceState)

        initData(_savedInstanceState)
        setAdapterCallBacks()
    }

    private fun initData(_savedInstanceState: Bundle?) {
        if (_savedInstanceState == null) {

            router.showProgress()
            getNews()
        }
    }

    @SuppressLint("CheckResult")
    fun getNews() {
        disposableManager.add(
            interactor.getNews(ALL_SECTIONS, PERIOD)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.uiScheduler())
            .subscribe(
                { news -> handleSuccess(news) },
                { handleError() })
        )
    }

    private fun handleSuccess(response: NewsResponse) {
        newsResponse = response
        if (response.results.isNotEmpty()) {
            populateData(response.results)
            view.setNewsAdapter(adapterNews)
        }
        else
            view.setError()
        router?.hideProgress()
    }

    private fun handleError() {
        router?.hideProgress()
        view.showToast(view.getStringValue(R.string.something_went_wrong))
        view.setError()
    }

    private fun populateData(newsList: List<NewsResults>?) {
        adapterNews.addAll(newsList)
    }

    private fun setAdapterCallBacks() {
        adapterNews.callback = {
            val news: NewsResults = adapterNews.getItem(it)
            openDetailsActivity(news.url, news.title)
        }
    }

    override fun openDetailsActivity(url: String?, title: String?) {
        router.startActivity(DetailsActivity::class.java, DetailsActivity.getBundle(url, title), false)
    }

    override fun onSaveData(_savedInstanceState: Bundle?) {
        _savedInstanceState?.putParcelable(BUNDLE_NEWS_MODEL, newsResponse)
    }

    override fun onRestoreData(_savedInstanceState: Bundle?) {
        newsResponse = _savedInstanceState?.getParcelable(BUNDLE_NEWS_MODEL)!!
        populateData(newsResponse.results)
        view.setNewsAdapter(adapterNews)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposableManager.dispose()
    }
}