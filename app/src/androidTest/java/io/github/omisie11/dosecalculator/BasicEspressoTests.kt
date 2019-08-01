package io.github.omisie11.dosecalculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BasicEspressoTests {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun typeDataGetResultsTest() {
        // Choose medicine
        onView(withId(R.id.chip_ibuprofen))
            .perform(click())

        onView(withId(R.id.edit_text_substance))
            .perform(clearText())
            .perform(typeText("40"), closeSoftKeyboard())

        onView(withId(R.id.edit_text_medicine))
            .perform(clearText())
            .perform(typeText("1"), closeSoftKeyboard())

        onView(withId(R.id.edit_text_mass))
            .perform(clearText())
            .perform(typeText("12"), closeSoftKeyboard())

        onView(withId(R.id.fab_calculate))
            .perform(click())

        onView(withId(R.id.text_alert_disclaimer))
            .check(matches(isDisplayed()))
    }

}