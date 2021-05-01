package com.assessment.ui.details

import dagger.Component
import dagger.Module
import dagger.Provides
import com.assessment.di.AppComponent
import com.assessment.di.scopes.ActivityScope

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(DetailsModule::class))
interface DetailsComponent {
    fun inject(activity: DetailsActivity)
}

@Module
class DetailsModule {
    @ActivityScope
    @Provides
    fun provideActivityPresenter(): DetailsContract.Presenter = DetailsPresenter()
}