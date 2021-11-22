package com.example.vinilosandroid.ui


import android.icu.util.RangeValueIterator
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.vinilosandroid.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions


@LargeTest
@RunWith(AndroidJUnit4::class)
class PE13NombreAlbumesConScroll {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE13NombreAlbumesConScroll() {
        Thread.sleep(2000)
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.albumFragment), withContentDescription("Albumes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomnav),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())
        Thread.sleep(2000)
        onView(withText("Buscando América"))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        Thread.sleep(2000)
        onView(withText("Poeta del pueblo"))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        Thread.sleep(2000)
        onView(withText("A Night at the Opera"))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))
        Thread.sleep(2000)
        onView(withText("A Day at the Races"))
            .perform(scrollTo())
            .check(ViewAssertions.matches(isDisplayed()))

    }


    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
