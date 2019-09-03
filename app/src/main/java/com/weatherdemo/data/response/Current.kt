package com.weatherdemo.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("last_updated_epoch")
    @Expose
    var lastUpdatedEpoch: Int? = null,
    @SerializedName("last_updated")
    @Expose
    var lastUpdated: String? = null,
    @SerializedName("temp_c")
    @Expose
    var tempC: Double? = null,
    @SerializedName("temp_f")
    @Expose
    var tempF: Double? = null,
    @SerializedName("is_day")
    @Expose
    var isDay: Int? = null

) {


}
