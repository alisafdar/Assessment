package com.assessment.data.repository

import com.assessment.data.models.response.NewsResponse
import io.reactivex.Single

interface NewsRepository {

    fun getNews(section: String, period: String): Single<NewsResponse>

}