package com.weatherdemo.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.weatherdemo.R
import com.weatherdemo.base.BaseFragment
import com.weatherdemo.rxBus.RxEvent
import com.weatherdemo.utils.AppConstants

class SplashFragment : BaseFragment() {

    private var handler: Handler? = null
    private var runnable: Runnable? = null

    private fun sendNextScreenEvent() {
        val event = RxEvent(RxEvent.EVENT_LOAD_HOME, null)
        rxBus.send(event)
    }

    override fun handleBusCallback(event: Any) {

    }

    override fun onDestroy() {
        handler!!.removeCallbacks(runnable)
        handler = null
        super.onDestroy()
    }

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragment_splash
    }

    override fun initViews(view: View) {
        handler = Handler()
        runnable = Runnable { this.sendNextScreenEvent() }
        handler!!.postDelayed(runnable, AppConstants.SPLASH_DELAY)
    }

    override fun resumeScreen() {

    }

    companion object {
        fun newInstance(): SplashFragment {
            val args = Bundle()
            val fragment = SplashFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

