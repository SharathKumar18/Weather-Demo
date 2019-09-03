package com.weatherdemo.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForeCast(@SerializedName("forecastday")
                                   @Expose
                                   private val forecastday: List<ForeCastDay>? = null) {
}
