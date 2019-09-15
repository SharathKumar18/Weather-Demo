package com.weatherdemo.home

import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.weatherdemo.R
import com.weatherdemo.ui.home.HomeActivity


class HomeActivityTest {

    @org.junit.Rule
    @JvmField
    var activityRule: ActivityTestRule<HomeActivity> =
        ActivityTestRule(HomeActivity::class.java)

    @org.junit.Test
    fun testActivityVisibleOnLaunch() {
        androidx.test.espresso.Espresso.onView(ViewMatchers.withId(R.id.homeContainer))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @org.junit.Test
    fun testHomeContainerVisible() {
        androidx.test.espresso.Espresso.onView(ViewMatchers.withId(R.id.homeContainer))
            .perform(setViewVisibility(true))
        androidx.test.espresso.Espresso.onView(ViewMatchers.withId(R.id.homeContainer))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    private fun setViewVisibility(value: Boolean): androidx.test.espresso.ViewAction {
        return object : androidx.test.espresso.ViewAction {
            override fun perform(uiController: androidx.test.espresso.UiController?, view: android.view.View?) {
                view?.visibility = if (value) android.view.View.VISIBLE else android.view.View.GONE
            }

            override fun getDescription(): String {
                return "Show / Hide View"
            }

            override fun getConstraints(): org.hamcrest.Matcher<android.view.View> {
                return ViewMatchers.isAssignableFrom(android.view.View::class.java)
            }
        }
    }
}
