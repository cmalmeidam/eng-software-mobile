package com.example.vinilosandroid.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
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

@LargeTest
@RunWith(AndroidJUnit4::class)
class PE05IconosBarraNavegacion {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE05IconosBarraNavegacion() {
        Thread.sleep(100)
        val imageView = onView(
            allOf(
                withId(R.id.icon),
                withParent(
                    allOf(
                        withId(R.id.albumFragment), withContentDescription("Álbumes"),
                        withParent(IsInstanceOf.instanceOf(android.view.View::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))
        Thread.sleep(100)
        val imageView2 = onView(
            allOf(
                withId(R.id.icon),
                withParent(
                    allOf(
                        withId(R.id.musicianFragment), withContentDescription("Artistas"),
                        withParent(IsInstanceOf.instanceOf(android.view.View::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))
        Thread.sleep(100)
        val imageView3 = onView(
            allOf(
                withId(R.id.icon),
                withParent(
                    allOf(
                        withId(R.id.collectorFragment), withContentDescription("Coleccionistas"),
                        withParent(IsInstanceOf.instanceOf(android.view.View::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView3.check(matches(isDisplayed()))
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
