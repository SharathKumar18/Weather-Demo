package com.weatherdemo.utils

import com.weatherdemo.BuildConfig


object Logger {

    private var isDebug = false

    init {
        isDebug = BuildConfig.DEBUG
    }

    fun e(tag: String, string: String) {
        if (isDebug) android.util.Log.e(tag, string)
    }

}
