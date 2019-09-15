package com.weatherdemo.model

import com.weatherdemo.data.response.Current
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrentClassTest {

    private lateinit var currentDataClass: Current

    @Before
    fun setup() {
        currentDataClass = Current(
            lastUpdatedEpoch = 30,
            lastUpdated = "Sunday",
            tempC = 24.0,
            tempF = 26.0,
            isDay = 3
        )
    }

    @Test
    fun testDataSetterAndGetter() {
        assertEquals(30, currentDataClass.lastUpdatedEpoch)
        assertEquals("Sunday", currentDataClass.lastUpdated)
        assertEquals(24.0, currentDataClass.tempC)
    }

    @Test
    fun testCurrentClassCopy() {
        val testedCopy = currentDataClass
        assertEquals(testedCopy, currentDataClass)
        assertEquals(testedCopy.hashCode(), currentDataClass.hashCode())
    }
}
