package com.vdc.assignment

import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

/**
 * Created by Huan.Huynh on 21/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
class WeatherListFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun verifyInitialState() {
        val activity = activityTestRule.activity

        val edtSearch = activity.findViewById<AppCompatEditText>(R.id.searchEdt)
        val btnSearch = activity.findViewById<AppCompatButton>(R.id.btnSearch)
        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)

        assertTrue(edtSearch.text.toString().isEmpty())
        assertFalse(btnSearch.isEnabled)
        assertEquals(0, recyclerView.adapter!!.itemCount)
    }

    @Test
    fun verifyEnableButtonByEditTextLength() {
        val activity = activityTestRule.activity

        val btnSearch = activity.findViewById<AppCompatButton>(R.id.btnSearch)

        onView(withId(R.id.searchEdt)).perform(replaceText("ABC"))
        assertTrue(btnSearch.isEnabled)

        Thread.sleep(2000)

        onView(withId(R.id.searchEdt)).perform(replaceText("AB"))
        assertFalse(btnSearch.isEnabled)

        Thread.sleep(2000)

        onView(withId(R.id.searchEdt)).perform(replaceText("ABCD"))
        assertTrue(btnSearch.isEnabled)
    }

    @Test
    fun verifySearchSuccess() {
        val activity = activityTestRule.activity

        val btnSearch = activity.findViewById<AppCompatButton>(R.id.btnSearch)
        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerView)

        onView(withId(R.id.searchEdt)).perform(replaceText("Saigon"))
        assertTrue(btnSearch.isEnabled)

        onView(withId(R.id.btnSearch)).perform(click())

        Thread.sleep(3000)

        assert(recyclerView.adapter!!.itemCount == 7)
    }
}