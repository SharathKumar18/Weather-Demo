package com.weatherdemo.network

import io.reactivex.observers.DisposableObserver

abstract class ResponseListener<T> : DisposableObserver<T>() {

    override fun onComplete() {}

    override fun onError(e: Throwable) {
        onFailure(e)
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    abstract fun onSuccess(response: T)

    abstract fun onFailure(error: Throwable)
}