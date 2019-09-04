package com.weatherdemo.utils

import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PreferenceHelperTest {

    @Mock
    var mockContext: Context? = null
    @Mock
    var mockPrefs: SharedPreferences? = null
    @Mock
    var mockEditor: SharedPreferences.Editor? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(mockContext?.getSharedPreferences(anyString(), anyInt())).thenReturn(mockPrefs)
        whenever(mockContext?.getSharedPreferences(anyString(), anyInt())?.edit()).thenReturn(mockEditor)
        whenever(mockPrefs?.getString("YOUR_KEY", null)).thenReturn("YOUR_VALUE")
    }

    @Test
    fun testPreferenceValueFloat() {
        whenever(mockPrefs?.edit()).thenReturn(mockEditor)
        assertEquals("YOUR_VALUE", mockPrefs?.getString("YOUR_KEY", null))
    }
}
