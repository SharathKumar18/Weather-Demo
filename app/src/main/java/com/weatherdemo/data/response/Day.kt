package com.weatherdemo.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("maxtemp_c")
    @Expose
    private val maxtempC: Double? = null,
    @SerializedName("maxtemp_f")
    @Expose
    private val maxtempF: Double? = null,
    @SerializedName("mintemp_c")
    @Expose
    private val mintempC: Double? = null,
    @SerializedName("mintemp_f")
    @Expose
    private val mintempF: Double? = null
) {


}