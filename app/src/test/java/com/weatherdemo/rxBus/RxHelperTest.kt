package com.weatherdemo.rxBus

import io.reactivex.observers.DisposableObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RxHelperTest {
    private lateinit var rxTestInstance: RxHelper
    @Before
    fun setup() {
        rxTestInstance = RxHelper()
        rxTestInstance.toObservable()?.share()?.subscribeWith(object : DisposableObserver<Any>() {
            override fun onNext(event: Any) {
            }
            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        })
    }

    @Test
    fun testRxHasObserversReturnsTrue() {
        Assert.assertEquals(true, rxTestInstance.hasObservers())
    }
}
