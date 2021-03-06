package com.example.vinilosandroid.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
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
class PE45CrearAlbumNoDuracion {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE45CrearAlbumNoDuracion() {
        Thread.sleep(1000)
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.albumFragment), withContentDescription("Álbumes"),
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
        onView(withText("Buscando América"))
            .perform(scrollTo())
            .perform(click())

        Thread.sleep(1000)
        val textView = onView(
            allOf(
                withId(R.id.textName), withText("Buscando América"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(withText("Buscando América")))
        onView(withText("Tracks"))
            .perform(scrollTo())
            .perform(click())

        Thread.sleep(1000)
        val appCompatEditText = onView(
                allOf(
                    withId(R.id.txtTrackName),
                    childAtPosition(
                        childAtPosition(
                            withClassName(`is`("android.widget.ScrollView")),
                            0
                        ),
                        1
                    )
                )
                )
        appCompatEditText.perform(scrollTo(), replaceText("Todos vuelven"), closeSoftKeyboard())

        onView(withText("Asociar"))
            .perform(scrollTo())
            .perform(click())
        val button = onView(
            allOf(
                withId(R.id.btnasociar), withText("Asociar"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        button.check(ViewAssertions.matches(isDisplayed()))
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
