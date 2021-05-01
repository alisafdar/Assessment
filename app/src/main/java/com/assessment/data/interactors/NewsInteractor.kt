package com.assessment.data.interactors

import com.assessment.data.models.response.NewsResponse
import com.assessment.data.repository.NewsRepository
import io.reactivex.Single
import com.assessment.di.scopes.ActivityScope
import javax.inject.Inject

@ActivityScope
class NewsInteractor @Inject constructor(private val newsRepository: NewsRepository) {

    fun getNews(section: String, period: String): Single<NewsResponse>
            = newsRepository.getNews(section, period)
}