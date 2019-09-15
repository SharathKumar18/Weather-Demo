package com.weatherdemo.splash

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.weatherdemo.R
import com.weatherdemo.ui.home.HomeActivity
import com.weatherdemo.ui.splash.SplashFragment
import com.weatherdemo.utils.AppConstants
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FragmentSplashTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        activityRule.activity
            .supportFragmentManager
            .beginTransaction()
            .add(SplashFragment(), "splashFragment")
            .commit()
    }

    @Test
    fun testSplashTitleVisibleOnLaunch() {
        Espresso.onView(ViewMatchers.withId(R.id.title))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.app_name)))
    }

    @Test
    @Throws(InterruptedException::class)
    fun testRedirectionToHome() {
        Thread.sleep(AppConstants.SPLASH_DELAY)
        Espresso.onView(ViewMatchers.withId(R.id.homeContainer))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}