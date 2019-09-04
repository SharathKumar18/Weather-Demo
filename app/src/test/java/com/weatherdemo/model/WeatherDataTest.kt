package com.weatherdemo.model

import com.weatherdemo.data.response.Current
import com.weatherdemo.data.response.WeatherDataClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherDataTest {
    private lateinit var weatherData: WeatherDataClass

    @Before
    fun setup() {
        weatherData = WeatherDataClass(null,Current(
            lastUpdatedEpoch = 30,
            lastUpdated = "Sunday",
            tempC = 24.0,
            tempF = 26.0,
            isDay = 3
        ))
    }

    @Test
    fun testDataSetterAndGetter() {
        Assert.assertEquals(30, weatherData.current?.lastUpdatedEpoch)
        Assert.assertEquals("Sunday", weatherData.current?.lastUpdated)
        Assert.assertEquals(24.0, weatherData.current?.tempC)
        Assert.assertEquals(3, weatherData.current?.isDay)
    }

    @Test
    fun testCurrentClassCopy() {
        val testedCopy = weatherData
        Assert.assertEquals(testedCopy, weatherData)
        Assert.assertEquals(testedCopy.hashCode(), weatherData.hashCode())
    }
}
