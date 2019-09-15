package com.weatherdemo.recyclerComponents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherdemo.data.response.ForeCastDay


class WeatherRecyclerAdapter(private var results: List<ForeCastDay>?) :
    RecyclerView.Adapter<WeatherItemViewHolder>() {

    override fun onBindViewHolder(holder: WeatherItemViewHolder, position: Int) {
        results?.get(position)?.let { holder.bindData(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(WeatherItemViewHolder.getLayout(), parent, false)
        return WeatherItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return results?.size ?: 0
    }

    fun updateItems(data: List<ForeCastDay>?) {
        results = data
        notifyDataSetChanged()
    }
}