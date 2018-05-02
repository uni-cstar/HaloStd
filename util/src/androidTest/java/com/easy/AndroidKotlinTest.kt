package com.easy

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Lucio on 17/12/5.
 */

@RunWith(AndroidJUnit4::class)
class AndroidKotlinTest{

    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getContext()
    }
}