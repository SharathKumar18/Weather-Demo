package com.weatherdemo.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForeCastDay(
    @SerializedName("date")
    @Expose
    private var date: String? = null,
    @SerializedName("date_epoch")
    @Expose
    private var dateEpoch: Int? = null,
    @SerializedName("day")
    @Expose
    private var day: Day? = null
) {


}