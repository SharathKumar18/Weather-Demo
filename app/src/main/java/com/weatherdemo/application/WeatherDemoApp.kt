package com.weatherdemo.application

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.weatherdemo.dagger.component.AppComponent
import com.weatherdemo.dagger.component.DaggerAppComponent
import com.weatherdemo.dagger.module.AppModule

class WeatherDemoApp : Application(), LifecycleObserver {

    private lateinit var appComponent: AppComponent

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        initDaggerComponent()
    }

    private fun initDaggerComponent() {
        appComponent= DaggerAppComponent.builder().appModule(AppModule()).build()
    }

    fun getApplicationComponent(): AppComponent {
        return appComponent
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