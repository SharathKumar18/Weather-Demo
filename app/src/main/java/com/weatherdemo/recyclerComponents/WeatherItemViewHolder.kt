package com.weatherdemo.recyclerComponents

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.weatherdemo.R
import com.weatherdemo.base.BaseViewHolder
import com.weatherdemo.data.response.ForeCastDay
import com.weatherdemo.rxBus.RxEvent
import java.text.SimpleDateFormat


class WeatherItemViewHolder(var view: View) : BaseViewHolder(view) {

    fun bindData(result: ForeCastDay) {
        val dayName = view.findViewById<TextView>(R.id.dayName)
        val temperature = view.findViewById<TextView>(R.id.temperature)

        val format1 = SimpleDateFormat("yyyy-MM-dd")
        val dt1 = format1.parse(result.date)
        val format2 = SimpleDateFormat("EEEE")
        val finalDay = format2.format(dt1)

        dayName.text = finalDay
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
