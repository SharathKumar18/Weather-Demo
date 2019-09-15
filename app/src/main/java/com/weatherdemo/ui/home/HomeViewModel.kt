package com.weatherdemo.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.weatherdemo.base.BaseViewModel
import com.weatherdemo.data.response.ForeCastDay
import com.weatherdemo.data.response.WeatherDataClass
import com.weatherdemo.network.ResponseListener
import com.weatherdemo.utils.ApiConstants.MAX_FORECAST
import com.weatherdemo.utils.AppConstants
import com.weatherdemo.utils.AppUtils

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
        val currentCity=preferenceHelper.getPrefString(AppConstants.KEY_CITY_NAME)
        if(AppUtils.isNetworkConnected() && currentCity!=null) {
            currentCity.let {
                dataManager.getWeatherData(
                    it,
                    object : ResponseListener<WeatherDataClass>() {
                        override fun onSuccess(response: WeatherDataClass) {
                            val requiredForeCastList = ArrayList<ForeCastDay>()
                            showForecastForLimitedDays(response, requiredForeCastList)
                            liveData.value = response
                            errorValue.value=false
                            hideProgress()
                        }

                        override fun onFailure(error: Throwable) {
                            errorValue.value=true
                            hideProgress()
                        }
                    })
            }
        }else{
            errorValue.value=true
        }
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
