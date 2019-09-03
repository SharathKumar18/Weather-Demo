package com.weatherdemo.application

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner

class WeatherDemoApp : Application(), LifecycleObserver {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {

    }

    companion object {
        private var instance: WeatherDemoApp? = null
        fun getContext(): WeatherDemoApp? {
            return instance
        }
    }
}