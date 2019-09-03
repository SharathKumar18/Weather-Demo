package com.weatherdemo.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.weatherdemo.application.WeatherDemoApp
import com.weatherdemo.data.model.UiHelper
import com.weatherdemo.network.DataManager
import com.weatherdemo.rxBus.RxHelper
import com.weatherdemo.utils.AppConstants
import com.weatherdemo.utils.PreferenceHelper
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

abstract class BaseViewModel (application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var rxBus: RxHelper
    @Inject
    lateinit var preferenceHelper : PreferenceHelper
    private var disposable: DisposableObserver<Any>? = null
    protected abstract fun handleBusCallback(event: Any)
    private val uiLiveData = MutableLiveData<UiHelper>()
    @Inject
    lateinit var dataManager: DataManager

    init {
        initDagger()
        registerForBusCallback()
    }

    fun getUiLiveData(): MutableLiveData<UiHelper> {
        return uiLiveData
    }

    private fun initDagger() {
        WeatherDemoApp.getContext()?.getApplicationComponent()?.inject(this)
    }

    fun showProgress() {
        val helper = UiHelper(AppConstants.UIConstants.SHOW_PROGRESS)
        uiLiveData.value=helper
    }

    fun hideProgress() {
        val helper = UiHelper(AppConstants.UIConstants.HIDE_PROGRESS)
        uiLiveData.value=helper
    }

    fun sendUiData(status: Int) {
        val helper = UiHelper(status)
        uiLiveData.value=helper
    }

    private fun registerForBusCallback() {
        disposable = object : DisposableObserver<Any>() {
            override fun onNext(event: Any) {
                handleBusCallback(event)
            }
            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        }
        rxBus.toObservable()?.share()?.subscribeWith(disposable)
    }
}
