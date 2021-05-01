package com.assessment.ui.main

import com.assessment.data.models.response.NewsResponse
import com.assessment.data.models.response.NewsResults
import com.assessment.data.repository.NewsRepository
import io.reactivex.Single
import io.reactivex.SingleEmitter

/**
 * Created by Tamas_Kozmer on 8/8/2017.
 */
class FakeUserRepository : NewsRepository {

    override fun getNews(section: String, period: String): Single<NewsResponse> {
        val users = (1..10L).map {
            val number = it
            NewsResults("20-01-2019", "Thompsans", "John", "Title $number", url = "1")
        }

        return Single.create<NewsResponse> { emitter: SingleEmitter<NewsResponse> ->
            val userListModel = NewsResponse(users)
            emitter.onSuccess(userListModel)
        }
    }
}