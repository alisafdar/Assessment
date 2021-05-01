package com.assessment.di.modules

import com.assessment.data.retrofit.RestApiClient
import com.assessment.data.retrofit.service.NewsApi
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideNewsApi(): NewsApi = RestApiClient.getInstance().newsApi()
}