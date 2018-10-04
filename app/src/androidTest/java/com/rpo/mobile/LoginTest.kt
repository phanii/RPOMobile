package com.rpo.mobile


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.rpo.mobile.login.Login
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(Login::class.java)

    @Test
    fun splashScreenTest() {

        onView(withId(R.id.username))
                .perform(typeText("phani@in.com"), closeSoftKeyboard())
        onView(withId(R.id.username)).perform(click())

        // Check that the text was changed.
        onView(withId(R.id.username)).check(matches(withText("phani@in.com")))
        //onView((withId(R.id.username))).check(matches(withText("phani@in.com")))
        //  editText.check(matches(withText("phani@in.com")))


        val editDrawableText = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                14),
                        isDisplayed()))
        editDrawableText.perform(replaceText("123456"), closeSoftKeyboard())

        val editText2 = onView(
                allOf(withId(R.id.password)))
        editText2.check(matches(withText("123456")))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

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
