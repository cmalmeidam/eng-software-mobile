package com.example.vinilosandroid.ui

import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.example.vinilosandroid.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test

class PE28CreacionAlbumNombreVacio {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE28creacionAlbumNombreVacio() {
        val botonCrearAlbum = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.createAlbumButton),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    1
                )
            )
        )
        botonCrearAlbum.perform(ViewActions.scrollTo(), ViewActions.click())

        val inputFechaDeLanzamiento = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.albumReleaseDate),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.frameLayout),
                        ViewMatchers.withParent(IsInstanceOf.instanceOf(ScrollView::class.java))
                    )
                ),
                ViewMatchers.isDisplayed()
            )
        )
        inputFechaDeLanzamiento.perform(
            ViewActions.replaceText("15/10/2010"),
            ViewActions.closeSoftKeyboard()
        )

        val inputURLCoverAlbum = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.albumcover),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.frameLayout),
                        ViewMatchers.withParent(IsInstanceOf.instanceOf(ScrollView::class.java))
                    )
                ),
                ViewMatchers.isDisplayed()
            )
        )
        inputURLCoverAlbum.perform(
            ViewActions.replaceText("https://m.media-amazon.com/images/I/81-wt1kTStL._SL1500_.jpg"),
        )

        Thread.sleep(50)

        val inputAlbumDescription = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.albumdescription),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.frameLayout),
                        ViewMatchers.withParent(IsInstanceOf.instanceOf(ScrollView::class.java))
                    )
                ),
                ViewMatchers.isDisplayed()
            )
        )
        inputAlbumDescription.perform(
            ViewActions.replaceText("Strokes Album"),
            ViewActions.closeSoftKeyboard()
        )


        val materialButton = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.crearAlbumBtn), ViewMatchers.withText("Crear Album"),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.frameLayout),
                        ViewMatchers.withParent(IsInstanceOf.instanceOf(ScrollView::class.java))
                    )
                )
            )
        ).perform(ViewActions.scrollTo())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        materialButton.perform(ViewActions.click())

        val button = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.crearAlbumBtn), ViewMatchers.withText("CREAR ALBUM"),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.frameLayout),
                        ViewMatchers.withParent(IsInstanceOf.instanceOf(ScrollView::class.java))
                    )
                ),
                ViewMatchers.isDisplayed()
            )
        )
        button.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

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