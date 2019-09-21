package com.weatherdemo.recyclerComponents

import android.view.View
import android.widget.TextView
import com.weatherdemo.R
import com.weatherdemo.base.BaseViewHolder
import com.weatherdemo.data.response.ForeCastDay
import com.weatherdemo.rxBus.RxEvent
import com.weatherdemo.utils.AppUtils


class WeatherItemViewHolder(private var view: View) : BaseViewHolder(view) {

    fun bindData(result: ForeCastDay) {
        val dayName = view.findViewById<TextView>(R.id.dayName)
        val temperature = view.findViewById<TextView>(R.id.temperature)
        dayName.text = result.date?.let { AppUtils.getDayFromDate(it) }
        temperature.text = result.day?.avgtempC.toString()
        view.setOnClickListener {
            onItemClicked(result)
        }
    }

    private fun onItemClicked(result: ForeCastDay) {
        val event = RxEvent(RxEvent.EVENT_ITEM_CLICKED, result)
        rxBus.send(event)
    }

    override fun handleBusCallback(event: Any) {
    }

    companion object {
        fun getLayout(): Int {
            return R.layout.weather_item
        }
    }
}
