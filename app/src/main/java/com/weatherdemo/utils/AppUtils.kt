package com.weatherdemo.utils

import android.content.Context
import android.net.ConnectivityManager
import com.weatherdemo.application.WeatherDemoApp
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            WeatherDemoApp.getContext()?.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getDayFromDate(date :String): String {
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dt1 = format1.parse(date)
        val format2 = SimpleDateFormat("EEEE",Locale.getDefault())
        return format2.format(dt1)
    }
}