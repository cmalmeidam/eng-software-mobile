package com.example.vinilosandroid.ui

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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
class AlbumFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun albumFragmentTest() {
        Thread.sleep(100)
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
        val textView = onView(
            allOf(
                withId(R.id.textView7), withText("Albumes para tí"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Albumes para tí")))
        Thread.sleep(1000)

        val imageView = onView(
            allOf(
                withId(R.id.item_cover_iv), withContentDescription("Buscando América"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))
        Thread.sleep(1000)
        val textView2 = onView(
            allOf(
                withId(R.id.textView5), withText("Buscando América"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Buscando América")))
        Thread.sleep(1000)

        val imageView2 = onView(
            allOf(
                withId(R.id.item_cover_iv), withContentDescription("Poeta del pueblo"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))
        Thread.sleep(1000)
        val textView4 = onView(
            allOf(
                withId(R.id.textView5), withText("Poeta del pueblo"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Poeta del pueblo")))
        Thread.sleep(1000)
        val imageView3 = onView(
            allOf(
                withId(R.id.item_cover_iv), withContentDescription("A Night at the Opera"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        imageView3.check(matches(isDisplayed()))
        Thread.sleep(1000)
        val textView3 = onView(
            allOf(
                withId(R.id.textView5), withText("A Night at the Opera"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("A Night at the Opera")))
        Thread.sleep(1000)
        val imageView4 = onView(
            allOf(
                withId(R.id.item_cover_iv), withContentDescription("A Day at the Races"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        imageView4.check(matches(isDisplayed()))

        val textView6 = onView(
            allOf(
                withId(R.id.textView5), withText("A Day at the Races"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("A Day at the Races")))
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