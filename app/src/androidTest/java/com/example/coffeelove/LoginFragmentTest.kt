package com.example.coffeelove

import android.support.test.runner.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.coffeelove.view.activity.MainActivity
import com.example.coffeelove.view.fragment.login.LoginFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class LoginFragmentTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkAccountFragment() {

        onView(withId(R.id.loginField))
        onView(withId(R.id.registerField))
        onView(withId(R.id.btn_login))


    }

}