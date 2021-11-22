package com.example.vinilosandroid.ui

import android.view.View
import android.view.ViewGroup
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
import org.junit.Rule
import org.junit.Test

class PE29CreacionUrlvacia {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE29creacionUrlVacia() {
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

        Thread.sleep(50)
        val inputNombreAlbum = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.albumname),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.frameLayout),
                        childAtPosition(
                            ViewMatchers.withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        inputNombreAlbum.perform(
            ViewActions.replaceText("Angles"),
            ViewActions.closeSoftKeyboard()
        )


        val inputFechaDeLanzamiento = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.albumReleaseDate),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.frameLayout),
                        childAtPosition(
                            ViewMatchers.withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    4
                ),
                ViewMatchers.isDisplayed()
            )
        )
        inputFechaDeLanzamiento.perform(
            ViewActions.replaceText("15/10/2010"),
            ViewActions.closeSoftKeyboard()
        )

        Thread.sleep(50)

        val inputAlbumDescription = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.albumdescription),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.frameLayout),
                        childAtPosition(
                            ViewMatchers.withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    3
                ),
                ViewMatchers.isDisplayed()
            )
        )
        inputAlbumDescription.perform(
            ViewActions.replaceText("Strokes Album"),
            ViewActions.closeSoftKeyboard()
        )

        Thread.sleep(50)

        val materialButton = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.crearAlbumBtn), ViewMatchers.withText("Crear Album"),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.frameLayout),
                        childAtPosition(
                            ViewMatchers.withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    5
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton.perform(ViewActions.click())


        val button = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.crearAlbumBtn), ViewMatchers.withText("CREAR ALBUM"),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.frameLayout),
                        ViewMatchers.withParent(ViewMatchers.withId(R.id.nav_host_fragment))
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