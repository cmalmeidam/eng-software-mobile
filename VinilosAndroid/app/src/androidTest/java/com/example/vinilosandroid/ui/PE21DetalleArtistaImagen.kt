package com.example.vinilosandroid.ui

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.vinilosandroid.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PE21DetalleArtistaImagen {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE21DetalleArtistaDescripcion() {
        Thread.sleep(1000)
        val bottomNavigationItemView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.musicianFragment),
                ViewMatchers.withContentDescription("Artistas"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.bottomnav),
                        0
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        bottomNavigationItemView.perform(ViewActions.click())
        Thread.sleep(1000)
        val textView2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.textView10),
                ViewMatchers.withText("Rubén Blades Bellido de Luna"),
                ViewMatchers.withParent(ViewMatchers.withParent(IsInstanceOf.instanceOf(CardView::class.java))),
                ViewMatchers.isDisplayed()
            )
        )
        textView2.perform(ViewActions.click())
        val imageView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.detail_image_iv),
                ViewMatchers.withContentDescription("Rubén Blades Bellido de Luna"),
                ViewMatchers.withParent(IsInstanceOf.instanceOf(LinearLayout::class.java)),
                ViewMatchers.isDisplayed()
            )
        )
        imageView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(1000)
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