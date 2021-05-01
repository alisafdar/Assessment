package com.assessment.di

import android.content.Context
import com.assessment.base.App
import com.assessment.data.SchedulerProvider
import dagger.Component
import com.assessment.di.modules.AndroidModule
import com.assessment.di.modules.AppMainModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppMainModule::class, AndroidModule::class))
interface AppComponent {

    fun inject(app: App)

    fun getContext(): Context

    fun getScheduleProvider() : SchedulerProvider

//    fun getSharedPrefManager(): SharedPrefController
//
//    fun getCacheManager(): CacheController
//
//    fun getDataBaseManager(): DatabaseController

//    fun getPermissionsManager(): PermissionsManager
}