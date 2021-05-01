package com.assessment.data.providers

import com.assessment.data.models.response.NewsResponse
import com.assessment.data.repository.NewsRepository
import com.assessment.data.retrofit.service.NewsApi
import io.reactivex.Single
import javax.inject.Inject

class NewsProvider @Inject constructor(val newsApi: NewsApi): NewsRepository {

    override fun getNews(section: String, period: String): Single<NewsResponse> {
        return newsApi.getNews(section, period)
    }
}