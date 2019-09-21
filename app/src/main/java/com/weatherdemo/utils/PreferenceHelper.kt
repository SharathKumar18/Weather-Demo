package com.weatherdemo.utils

import android.content.Context
import android.content.SharedPreferences
import com.weatherdemo.application.WeatherDemoApp


class PreferenceHelper {

    private var preference: SharedPreferences? = null

    init {
        preference = WeatherDemoApp.getContext()?.getSharedPreferences(AppConstants.PREF_NAME,
            Context.MODE_PRIVATE)
    }

    fun editPrefString(name: String, value: String) {
        preference!!.edit().putString(name, value).apply()
    }

    fun editPrefLong(name: String, value: Float) {
        preference!!.edit().putFloat(name, value).apply()
    }

    fun getPrefString(name: String): String? {
        return preference!!.getString(name, null)
    }
}