package com.assessment.di.modules

import android.content.Context
import com.assessment.data.AppSchedulerProvider
import com.assessment.data.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppMainModule(val context: Context) {

    @Provides
    @Singleton
    fun provideSchedulerProvider() : SchedulerProvider = AppSchedulerProvider()

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}


