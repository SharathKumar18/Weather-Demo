package com.weatherdemo.model

import com.weatherdemo.data.model.UiHelper
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UiHelperTest {

    private lateinit var uiHelper: UiHelper

    @Before
    fun setup() {
        uiHelper = UiHelper(1,"data received")
    }

    @Test
    fun testDataSetterAndGetter() {
        Assert.assertEquals(1, uiHelper.status)
        Assert.assertEquals("data received", uiHelper.message)
    }

    @Test
    fun testCurrentClassCopy() {
        val testedCopy = uiHelper
        Assert.assertEquals(testedCopy, uiHelper)
        Assert.assertEquals(testedCopy.hashCode(), uiHelper.hashCode())
    }
}
