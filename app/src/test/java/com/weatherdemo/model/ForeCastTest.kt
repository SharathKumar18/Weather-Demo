package com.weatherdemo.model

import com.weatherdemo.data.response.ForeCast
import com.weatherdemo.data.response.ForeCastDay
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ForeCastTest {

    private lateinit var foreCast: ForeCast

    @Before
    fun setup() {
        val list= ArrayList<ForeCastDay>()
        list.add(ForeCastDay("04/09/2019"))
        foreCast = ForeCast(list)
    }

    @Test
    fun testDataSetterAndGetter() {
        Assert.assertEquals(1, foreCast.forecastday?.size)
        Assert.assertEquals("04/09/2019", foreCast.forecastday?.get(0)?.date)
    }

    @Test
    fun testCurrentClassCopy() {
        val testedCopy = foreCast
        Assert.assertEquals(testedCopy, foreCast)
        Assert.assertEquals(testedCopy.hashCode(), foreCast.hashCode())
    }
}
