package com.weatherdemo.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.weatherdemo.base.BaseViewModel
import com.weatherdemo.data.response.ForeCastDay
import com.weatherdemo.data.response.WeatherDataClass
import com.weatherdemo.network.ResponseListener
import com.weatherdemo.utils.ApiConstants.MAX_FORECAST

class HomeViewModel(application: Application) : BaseViewModel(application = application) {

    private val liveData = MutableLiveData<WeatherDataClass>()
    private val errorValue: MutableLiveData<Boolean> = MutableLiveData()

    fun getRestaurantLiveData(): MutableLiveData<WeatherDataClass> {
        return liveData
    }

    fun getErrorLiveData(): MutableLiveData<Boolean> {
        return errorValue
    }

    fun fetchRestaurantData() {
        showProgress()
        errorValue.value = false
        dataManager.getWeatherData(
            "London",
            object : ResponseListener<WeatherDataClass>() {
                override fun onSuccess(response: WeatherDataClass) {
                    hideProgress()
                    val requiredForeCastList=ArrayList<ForeCastDay>()
                    showForecastForLimitedDays(response, requiredForeCastList)
                    liveData.value = response
                }

                override fun onFailure(error: Throwable) {
                    hideProgress()
                }
            })
    }

    private fun showForecastForLimitedDays(
        response: WeatherDataClass,
        requiredForeCastList: ArrayList<ForeCastDay>
    ) {
        for (i in 1..MAX_FORECAST) {
            response.forecast?.forecastday?.get(i)?.let { requiredForeCastList?.add(it) }
        }
        response.forecast?.forecastday = requiredForeCastList
    }

    override fun handleBusCallback(event: Any) {

    }

}
