package com.weatherdemo.network

import com.weatherdemo.utils.ApiConstants
import java.util.*

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
        queryMap[ApiConstants.KEY] = ApiConstants.API_KEY
        queryMap[ApiConstants.Q] = city
        queryMap[ApiConstants.DAYS] = ApiConstants.DEFAULT_DAYS
        return queryMap
    }
}