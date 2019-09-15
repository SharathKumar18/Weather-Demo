package com.weatherdemo.rxBus

data class RxEvent<T>(var eventTag: Int = 0, var data: T? = null) {

    companion object {
        const val EVENT_LOAD_HOME = 1
        const val EVENT_LOCATION_UPDATED = 2
        const val EVENT_ITEM_CLICKED = 3
    }
}