package com.assessment.ui.main

import com.assessment.R
import com.assessment.data.models.response.NewsResponse
import com.assessment.testutil.TestSchedulerProvider
import io.reactivex.Single
import com.assessment.data.interactors.NewsInteractor
import com.assessment.constants.ALL_SECTIONS
import com.assessment.constants.PERIOD
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class MainPresenterTest {

    @Mock
    lateinit var mockNewsInteractor: NewsInteractor

    @Mock
    lateinit var mockView: MainContract.View

    lateinit var mainPresenter: MainPresenter

    lateinit var testSchedulerProvider: TestSchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testSchedulerProvider = TestSchedulerProvider()
        mainPresenter = MainPresenter(mockNewsInteractor, testSchedulerProvider)
    }

    @Test
    fun testGetNews_errorCase_showError() {

        val error = "Test error"
        val single: Single<NewsResponse> = Single.create {
            emitter ->
            emitter.onError(Exception(error))
        }

        whenever(mockNewsInteractor.getNews(ALL_SECTIONS, PERIOD)).thenReturn(single)

        mainPresenter.bindView(mockView)
        mainPresenter.getNews()

        testSchedulerProvider.testScheduler.triggerActions()

        verify(mockView).showToast(mockView.getStringValue(R.string.something_went_wrong))
        verify(mockView).setError()
    }
}