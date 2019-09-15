package com.weatherdemo.network

import com.weatherdemo.utils.ApiConstants
import java.util.HashMap

object NetworkQueryMapper {

    fun getURL(apiCall: String): String? {
        var baseURL = ApiConstants.BASE_URL
        when (apiCall) {
            ApiConstants.GET_FORECAST -> {
                baseURL += ApiConstants.FORECAST
                return baseURL
            }
        }
        return null
    }

    fun getWeatherQuery(city: String): Map<String, String> {
        val queryMap = HashMap<String, String>()
        queryMap.put(ApiConstants.KEY,ApiConstants.API_KEY)
        queryMap.put(ApiConstants.Q,city)
        queryMap.put(ApiConstants.DAYS,ApiConstants.DEFAULT_DAYS)
        return queryMap
    }
}