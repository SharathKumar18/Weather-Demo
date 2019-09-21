package com.weatherdemo.utils

object AppConstants {

    const val SPLASH_DELAY: Long = 3000
    const val GET_LOCATION = 1
    const val KEY_LONGITUDE = "longitude"
    const val KEY_LATITUDE = "latitude"
    const val KEY_CITY_NAME = "cityName"
    const val PREF_NAME = "WeatherAppPreference"

    interface UIConstants {
        companion object {
            const val SHOW_PROGRESS = 1
            const val HIDE_PROGRESS = 2
        }
    }
}