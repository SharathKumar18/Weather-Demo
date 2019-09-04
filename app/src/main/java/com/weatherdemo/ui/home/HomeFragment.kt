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


class HomeFragment : BaseFragment() {

    private var restaurantAdapter: WeatherRecyclerAdapter? = null
    private lateinit var layoutManager: LinearLayoutManager

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initViews(view: View) {
        observeLiveData()
        setUpRecyclerView(null)
        getViewModel()?.fetchRestaurantData()
    }

    private fun observeLiveData() {
        getViewModel()?.getRestaurantLiveData()?.observe(this, Observer<WeatherDataClass> {
            updateView(it)
        })
        getViewModel()?.getUiLiveData()?.observe(this,
            Observer<UiHelper> { t -> t?.let { handleUICallbacks(uiHelper = it) } })

        getViewModel()?.getErrorLiveData()?.observe(this, Observer {
            if (it) {
                errorText.visibility = View.VISIBLE
                errorText.text = getString(R.string.error_text)
            } else {
                errorText.visibility = View.GONE
                errorText.text = ""
            }
        })
    }

    private fun updateView(it: WeatherDataClass) {
        weatherToday.text = it.current?.tempC.toString()
        location.text = it.location?.name.toString()
        if (restaurantAdapter == null) {
            setUpRecyclerView(it.forecast?.forecastday)
        } else {
            restaurantAdapter?.updateItems(it.forecast?.forecastday)
        }
        val animMoveToTop : Animation = AnimationUtils.loadAnimation(context, R.anim.reveal_recycler_view)
        forecastRecyclerView.startAnimation(animMoveToTop)

    }

    override fun resumeScreen() {
    }

    override fun handleBusCallback(event: Any) {
        if (event is RxEvent<*>) {
            when (event.eventTag) {
                RxEvent.EVENT_LOCATION_UPDATED -> {
                    if (event.data is Location && isResumed) {
                        getViewModel()?.fetchRestaurantData()
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
                progressCircular.visibility = View.VISIBLE
            AppConstants.UIConstants.HIDE_PROGRESS -> progressCircular.visibility = View.GONE
        }
    }

    fun getFragmentData(): HomeViewModel? {
        return getViewModel()
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
