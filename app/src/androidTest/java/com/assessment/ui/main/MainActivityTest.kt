package com.assessment.ui.main


import android.app.Activity
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.assessment.R
import com.assessment.base.App
import com.assessment.data.repository.NewsRepository
import com.assessment.ui.details.DetailsActivity
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        newsRepository = mock()

        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as App

//        val testComponent = DaggerFakeApplicationComponent.builder()
//            .fakeApplicationModule(FakeApplicationModule(newsRepository))
//            .build()
//        App.component = testComponent
    }

    @Test
    fun navigate() {

        onView(withId(R.id.contentView)).perform(click())

        val activity = getActivityInstance()
        val b = activity is DetailsActivity
        assertTrue(b)
    }

    fun getActivityInstance(): Activity {
        val activity = arrayOfNulls<Activity>(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            var currentActivity: Activity? = null
            val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = resumedActivities.iterator().next() as Activity
                activity[0] = currentActivity
            }
        }

        return activity[0]!!
    }
}
