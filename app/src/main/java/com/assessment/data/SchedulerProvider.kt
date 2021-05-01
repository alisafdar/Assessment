package com.assessment.data

import io.reactivex.Scheduler
import com.assessment.di.scopes.ActivityScope

@ActivityScope
interface SchedulerProvider {
    fun uiScheduler() : Scheduler
    fun ioScheduler() : Scheduler
}