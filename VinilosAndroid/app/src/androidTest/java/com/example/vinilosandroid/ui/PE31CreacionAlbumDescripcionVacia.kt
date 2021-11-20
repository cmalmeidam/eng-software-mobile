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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PE31CreacionAlbumDescripcionVacia {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun pE31CreacionAlbumDescripcionVacia() {
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

        Thread.sleep(50)


        val inputNombreAlbum = onView(
            allOf(
                withId(R.id.albumname),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        inputNombreAlbum.perform(replaceText("Angles"), closeSoftKeyboard())

        Thread.sleep(50)

        val inputFechaDeLanzamiento = onView(
            allOf(
                withId(R.id.albumReleaseDate),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        inputFechaDeLanzamiento.perform(replaceText("15/10/2010"), closeSoftKeyboard())

        Thread.sleep(50)

        val inputURLCoverAlbum = onView(
            allOf(
                withId(R.id.albumcover),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )

        inputURLCoverAlbum.perform(
            replaceText("https://m.media-amazon.com/images/I/81-wt1kTStL._SL1500_.jpg"),
        )

        Thread.sleep(50)

        val materialButton = onView(
            allOf(
                withId(R.id.crearAlbumBtn), withText("Crear Album"),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout),
                        childAtPosition(
                            withId(R.id.nav_host_fragment),
                            0
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())


        val button = onView(
            allOf(
                withId(R.id.crearAlbumBtn), withText("CREAR ALBUM"),
                withParent(
                    allOf(
                        withId(R.id.frameLayout),
                        withParent(withId(R.id.nav_host_fragment))
                    )
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))
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
