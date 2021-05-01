package com.assessment.ui.main

import dagger.Component
import com.assessment.di.AppComponent
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 8/8/2017.
 */
@Singleton
@Component(modules = arrayOf(FakeApplicationModule::class))
interface FakeApplicationComponent : AppComponent