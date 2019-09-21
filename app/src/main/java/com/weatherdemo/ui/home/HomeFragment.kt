package com.weatherdemo.ui.home

import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherdemo.R
import com.weatherdemo.base.BaseFragment
import com.weatherdemo.data.model.UiHelper
import com.weatherdemo.data.response.ForeCastDay
import com.weatherdemo.data.response.WeatherDataClass
import com.weatherdemo.recyclerComponents.WeatherRecyclerAdapter
import com.weatherdemo.rxBus.RxEvent
import com.weatherdemo.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_error.view.*
import android.view.View.VISIBLE as VISIBLE1


class HomeFragment : BaseFragment() {

    private var restaurantAdapter: WeatherRecyclerAdapter? = null
    private lateinit var layoutManager: LinearLayoutManager

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initViews(view: View) {
        observeLiveData()
        setUpRecyclerView(null)
        setErrorButtonClickListener()
        getViewModel()?.fetchWeatherInfoFromServer()
    }

    private fun setErrorButtonClickListener() {
        errorLayout.errorButton.setOnClickListener {
            getViewModel()?.fetchWeatherInfoFromServer()
            errorLayout.visibility = View.GONE
        }
    }

    private fun observeLiveData() {
        getViewModel()?.getRestaurantLiveData()?.observe(this, Observer<WeatherDataClass> {
            updateView(it)
        })
        getViewModel()?.getUiLiveData()?.observe(this,
            Observer<UiHelper> { t -> t?.let { handleUICallbacks(uiHelper = it) } })


        getViewModel()?.getErrorLiveData()?.observe(this, Observer {
            if (it) {
                errorLayout.visibility = View.VISIBLE
            } else {
                errorLayout.visibility = View.GONE
            }
        })
    }

    private fun updateView(it: WeatherDataClass) {
        weatherToday.text = StringBuilder().append(it.current?.tempC?.toInt())
            .append(getString(R.string.symbol_degree))
        location.text = it.location?.name.toString()
        if (restaurantAdapter == null) {
            setUpRecyclerView(it.forecast?.forecastday)
        } else {
            restaurantAdapter?.updateItems(it.forecast?.forecastday)
        }
        val animMoveToTop: Animation =
            AnimationUtils.loadAnimation(context, R.anim.reveal_recycler_view)
        forecastRecyclerView.startAnimation(animMoveToTop)

    }

    override fun resumeScreen() {
    }

    override fun handleBusCallback(event: Any) {
        if (event is RxEvent<*>) {
            when (event.eventTag) {
                RxEvent.EVENT_LOCATION_UPDATED -> {
                    if (event.data is Location && isResumed) {
                        getViewModel()?.fetchWeatherInfoFromServer()
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(results: List<ForeCastDay>?) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        forecastRecyclerView.layoutManager = layoutManager
        restaurantAdapter = WeatherRecyclerAdapter(results)
        forecastRecyclerView.adapter = restaurantAdapter
        forecastRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }


    private fun handleUICallbacks(uiHelper: UiHelper) {
        when (uiHelper.status) {
            AppConstants.UIConstants.SHOW_PROGRESS ->
                progressLayout.visibility = VISIBLE1
            AppConstants.UIConstants.HIDE_PROGRESS -> progressLayout.visibility = View.GONE
        }
    }

    private fun getViewModel(): HomeViewModel? {
        return ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
