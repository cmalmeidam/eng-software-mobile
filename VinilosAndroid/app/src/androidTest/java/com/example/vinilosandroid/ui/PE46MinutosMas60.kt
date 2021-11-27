package com.example.vinilosandroid.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
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
class PE46MinutosMas60 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE46MinutosMas60() {
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
        Thread.sleep(1000)
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
        Thread.sleep(1000)
        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.txtMinutos),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText2.perform(scrollTo(), replaceText("61"), closeSoftKeyboard())
        Thread.sleep(1000)
        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.txtSegundos),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    5
                )
            )
        )
        appCompatEditText3.perform(scrollTo(), replaceText("59"), closeSoftKeyboard())
        Thread.sleep(1000)
        onView(withText("Asociar"))
            .perform(scrollTo())
            .perform(click())
        Thread.sleep(1000)
        val button1 = onView(
            allOf(
                withId(R.id.btnasociar), withText("Asociar"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        button1.check(ViewAssertions.matches(isDisplayed()))
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
