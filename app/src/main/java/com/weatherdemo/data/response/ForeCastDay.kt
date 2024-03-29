package com.weatherdemo.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForeCastDay(
    @SerializedName("date")
    @Expose
    var date: String? = null,
    @SerializedName("day")
    @Expose
    var day: Day? = null
)