package com.weatherdemo.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForeCast(@SerializedName("forecastday")
                                   @Expose
                                   var forecastday: List<ForeCastDay>? = null) {
}
