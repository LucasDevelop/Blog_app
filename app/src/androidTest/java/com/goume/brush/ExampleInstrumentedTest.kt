package com.goume.brush

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.net.HttpURLConnection
import java.net.URL

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getTargetContext()
//        assertEquals("com.goume.brush", appContext.packageName)
        val urlConnection = URL("https://blog.csdn.net/lucas19911226/article/details/86729973").openConnection() as HttpURLConnection
        urlConnection.connect()
        urlConnection.inputStream
        urlConnection.disconnect()
    }
}
