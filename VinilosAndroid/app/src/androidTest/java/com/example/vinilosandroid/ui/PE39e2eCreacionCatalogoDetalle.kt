package com.example.vinilosandroid.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
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
class PE39e2eCreacionCatalogoDetalle {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE39e2eCreacionCatalogoDetalle() {
        val botonCrearAlbum = onView(
            allOf(
                withId(R.id.createAlbumButton),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    1
                )
            )
        )
        botonCrearAlbum.perform(scrollTo(), click())
        val rnds = (0..1000).random()

        val name = """Angles $rnds"""
        val inputNombreAlbum = onView(
            allOf(
                withId(R.id.albumname),
                withParent(
                    allOf(
                        withId(R.id.frameLayout),
                        withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        inputNombreAlbum.perform(replaceText("$name"), closeSoftKeyboard())

        val inputFechaDeLanzamiento = onView(
            allOf(
                withId(R.id.albumRelease),
                withParent(
                    allOf(
                        withId(R.id.frameLayout),
                        withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        inputFechaDeLanzamiento.perform(replaceText("15/10/2010"), closeSoftKeyboard())

        val inputURLCoverAlbum = onView(
            allOf(
                withId(R.id.albumcover),
                withParent(
                    allOf(
                        withId(R.id.frameLayout),
                        withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        inputURLCoverAlbum.perform(
            replaceText("https://m.media-amazon.com/images/I/81-wt1kTStL._SL1500_.jpg"),
        )


        Thread.sleep(50)

        val inputAlbumDescription = onView(
            allOf(
                withId(R.id.albumdescription),
                withParent(
                    allOf(
                        withId(R.id.frameLayout),
                        withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        inputAlbumDescription.perform(replaceText("Strokes Album"), closeSoftKeyboard())


        val materialButton = onView(
            allOf(
                withId(R.id.crearAlbumBtn), withText("Crear ??lbum"),
                withParent(
                    allOf(
                        withId(R.id.frameLayout),
                        withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))
                    )
                ))).perform(scrollTo())
            .check(matches(isDisplayed()))

        materialButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.textName), withText("$name"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("$name")))
        Thread.sleep(1000)
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.albumFragment), withContentDescription("??lbumes"),
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
