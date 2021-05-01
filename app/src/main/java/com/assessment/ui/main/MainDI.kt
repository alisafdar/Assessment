package com.assessment.ui.main

import com.assessment.data.SchedulerProvider
import com.assessment.data.providers.NewsProvider
import com.assessment.data.repository.NewsRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import com.assessment.di.AppComponent
import com.assessment.di.modules.ApiModule
import com.assessment.di.scopes.ActivityScope
import com.assessment.data.interactors.NewsInteractor

@ActivityScope
@Component(dependencies = [AppComponent::class],
        modules = [MainModule::class]
)
interface MainComponent {
    fun inject(activity: MainActivity)
}

@Module(includes = [ApiModule::class])
class MainModule {

    @ActivityScope
    @Provides
    fun provideActivityPresenter(interactor: NewsInteractor, schedulerProvider: SchedulerProvider): MainContract.Presenter = MainPresenter(interactor, schedulerProvider)

    @ActivityScope
    @Provides
    fun provideBasketRepo(provider: NewsProvider): NewsRepository = provider

    @ActivityScope
    @Provides
    fun provideBasketInteractor(provider: NewsProvider): NewsInteractor
            = NewsInteractor(provider)

}