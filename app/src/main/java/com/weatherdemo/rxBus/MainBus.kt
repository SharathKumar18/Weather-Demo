package com.weatherdemo.rxBus

import io.reactivex.subjects.PublishSubject

interface MainBus {
    fun send(event: Any)
    fun toObservable(): PublishSubject<Any>?
    fun hasObservers(): Boolean?
}
