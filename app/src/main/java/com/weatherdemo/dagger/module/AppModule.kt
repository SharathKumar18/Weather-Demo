package com.weatherdemo.dagger.module

import com.weatherdemo.rxBus.RxHelper
import com.weatherdemo.utils.PreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun getRxHelper(): RxHelper = RxHelper()

    @Provides
    @Singleton
    fun getPreferenceHelper(): PreferenceHelper = PreferenceHelper()
}