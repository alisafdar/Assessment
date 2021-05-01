package com.assessment.ui.main

import com.assessment.data.repository.NewsRepository
import com.assessment.data.AppSchedulerProvider
import com.assessment.data.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 8/8/2017.
 */
@Module
class FakeApplicationModule(val newsRepository: NewsRepository) {

    @Provides
    @Singleton
    fun provideUserRepository() : NewsRepository {
        return newsRepository
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider() : SchedulerProvider = AppSchedulerProvider()
}