package com.weatherdemo.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.weatherdemo.data.model.UiHelper
import com.weatherdemo.utils.AppConstants
import io.reactivex.observers.DisposableObserver

abstract class BaseViewModel (application: Application) : AndroidViewModel(application) {

    private var disposable: DisposableObserver<Any>? = null
    protected abstract fun handleBusCallback(event: Any)
    private val uiLiveData = MutableLiveData<UiHelper>()

    init {
        registerForBusCallback()
    }

    fun getUiLiveData(): MutableLiveData<UiHelper> {
        return uiLiveData
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
    }
}
