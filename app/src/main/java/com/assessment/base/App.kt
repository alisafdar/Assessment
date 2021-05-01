package com.assessment.base

import android.app.Application
import android.content.Context
import com.assessment.di.AppComponent
import com.assessment.di.DaggerAppComponent
import com.assessment.di.modules.AppMainModule

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        component = createComponent()
        component.inject(this)
    }

    private fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appMainModule(AppMainModule(this))
                .build()
    }


    companion object {
        @JvmStatic
        lateinit var appContext: Context
            private set

        lateinit var component: AppComponent
    }
}