package com.weatherdemo.network

import com.weatherdemo.application.WeatherDemoApp
import com.weatherdemo.utils.ApiConstants
import com.weatherdemo.utils.ApiConstants.GET_FORECAST
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.util.HashMap

class NetworkQueryMapperTest {

    private lateinit var networkMapper: NetworkQueryMapper

    @Before
    fun setup() {
        networkMapper = NetworkQueryMapper
    }

    @Test
    fun testBaseUrlIsEqual() {
        Assert.assertEquals("http://api.apixu.com/v1/forecast.json", networkMapper.getURL(GET_FORECAST))
    }

    @Test
    fun testGetWeatherQueryEqual() {
        Assert.assertEquals("sample", networkMapper.getWeatherQuery("sample").get(ApiConstants.Q))
    }
}
