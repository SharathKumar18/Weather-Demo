package com.weatherdemo.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherDataClass(
    @SerializedName("location")
    @Expose
    var location:Location?=null,
    @SerializedName("current")
    @Expose
    var current: Current? = null,
    @SerializedName("forecast")
    @Expose
    var forecast: ForeCast? = null
) :BaseModel()
