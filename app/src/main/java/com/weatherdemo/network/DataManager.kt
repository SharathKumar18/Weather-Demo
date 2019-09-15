package com.weatherdemo.network

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.weatherdemo.data.response.WeatherDataClass
import com.weatherdemo.utils.ApiConstants
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DataManager(private val apiService: RetrofitInterface) {

    fun getWeatherData(
        resID: String,
        callback: ResponseListener<WeatherDataClass>
    ): Disposable? {
        return NetworkQueryMapper.getURL(ApiConstants.GET_FORECAST)?.let {
            apiService.getWeatherData(
                it,
                NetworkQueryMapper.getWeatherQuery(resID)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(ObservableSource { callback.onFailure(Throwable()) })
                .subscribeWith(callback)
        }
    }
}
