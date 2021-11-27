package com.example.vinilosandroid.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.vinilosandroid.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PE49AvisoNoHayTracks {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE49AvisoNoHayTracks() {
        Thread.sleep(1000)
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
        Thread.sleep(1000)
        onView(withText("Poeta del pueblo"))
            .perform(scrollTo())
            .perform(click())

        Thread.sleep(1000)
        val textView = onView(
            allOf(
                withId(R.id.textName), withText("Poeta del pueblo"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Poeta del pueblo")))
        onView(withText("Tracks"))
            .perform(scrollTo())
            .perform(click())

        Thread.sleep(1000)
        onView(withText("No hay tracks asociados a este álbum")).perform(scrollTo()).check(ViewAssertions.matches(isDisplayed()))

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
