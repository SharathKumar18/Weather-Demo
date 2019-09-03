package com.weatherdemo.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.observers.DisposableObserver

abstract class BaseViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    private var disposable: DisposableObserver<Any>? = null
    protected abstract fun handleBusCallback(event: Any)

    init {
        registerForBusCallback()
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