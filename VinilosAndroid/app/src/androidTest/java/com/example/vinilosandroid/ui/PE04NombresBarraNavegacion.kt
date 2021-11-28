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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PE04NombresBarraNavegacion {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE04NombresBarraNavegacion() {
        Thread.sleep(100)
        val textView = onView(
            allOf(
                withId(R.id.largeLabel), withText("Albumes"),
                withParent(
                    withParent(
                        allOf(
                            withId(R.id.albumFragment),
                            withContentDescription("Albumes")
                        )
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Albumes")))
        Thread.sleep(100)
        val textView2 = onView(
            allOf(
                withId(R.id.smallLabel), withText("Artistas"),
                withParent(
                    withParent(
                        allOf(
                            withId(R.id.musicianFragment),
                            withContentDescription("Artistas")
                        )
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Artistas")))
        Thread.sleep(100)
        val textView3 = onView(
            allOf(
                withId(R.id.smallLabel), withText("Coleccionistas"),
                withParent(
                    withParent(
                        allOf(
                            withId(R.id.collectorFragment),
                            withContentDescription("Coleccionistas")
                        )
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Coleccionistas")))
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
