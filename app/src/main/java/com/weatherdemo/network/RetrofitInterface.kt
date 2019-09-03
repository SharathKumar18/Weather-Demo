package com.weatherdemo.network

import com.weatherdemo.data.response.WeatherDataClass
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface RetrofitInterface {

    @GET
    fun getWeatherData(@Url path: String, @QueryMap query: Map<String, String>): Observable<WeatherDataClass>
}