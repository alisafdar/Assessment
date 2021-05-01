package com.assessment.data.interactors

import com.assessment.data.models.response.NewsResponse
import com.assessment.data.models.response.NewsResults
import com.assessment.data.repository.NewsRepository
import io.reactivex.Single
import io.reactivex.SingleEmitter
import com.assessment.constants.ALL_SECTIONS
import com.assessment.constants.PERIOD
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class GetNewsTest {

    @Mock
    lateinit var mockUserRepository: NewsRepository
    lateinit var newsInteractor: NewsInteractor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        newsInteractor = NewsInteractor(mockUserRepository)
    }

    @Test
    fun testExecute_newsListModelWithOneItem_emitListWithOneViewModel() {
        val newsListModel = NewsResponse(listOf(NewsResults("2019-07-23", "The New York Times", "By SCOTT SHANE and SARAH KLIFF", "Neil Armstrong’s Death, and a Stormy, Secret \$6 Million Settlement", "https:\\/\\/www.nytimes.com\\/2019\\/07\\/23\\/us\\/neil-armstrong-wrongful-death-settlement.html")))
        setUpStubbing(newsListModel)

        val testObserver = newsInteractor.getNews(ALL_SECTIONS, PERIOD).test()

        testObserver.assertNoErrors()
        testObserver.assertValue { newsResponse: NewsResponse -> newsResponse.results.size == 1 }
        testObserver.assertValue { newsResponse: NewsResponse ->
            newsResponse.results.get(0) == NewsResults("2019-07-23", "The New York Times", "By SCOTT SHANE and SARAH KLIFF", "Neil Armstrong’s Death, and a Stormy, Secret \$6 Million Settlement", "https:\\/\\/www.nytimes.com\\/2019\\/07\\/23\\/us\\/neil-armstrong-wrongful-death-settlement.html") }
    }

    @Test
    fun testExecute_userListModelEmpty_emitEmptyList() {
        val userListModel = NewsResponse(emptyList())
        setUpStubbing(userListModel)

        val testObserver = newsInteractor.getNews(ALL_SECTIONS, PERIOD).test()

        testObserver.assertNoErrors()
        testObserver.assertValue { newsResponse: NewsResponse -> newsResponse.results.isEmpty() }
    }

    private fun setUpStubbing(userListModel: NewsResponse) {
        val mockSingle = Single.create { e: SingleEmitter<NewsResponse>? -> e?.onSuccess(userListModel) }

        whenever(mockUserRepository.getNews(ALL_SECTIONS, PERIOD))
                .thenReturn(mockSingle)
    }
}