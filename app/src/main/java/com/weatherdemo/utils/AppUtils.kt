package com.weatherdemo.utils

import android.content.Context
import android.net.ConnectivityManager
import com.weatherdemo.application.WeatherDemoApp

object AppUtils {

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            WeatherDemoApp.getContext()?.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}