package com.weatherdemo.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.weatherdemo.R
import com.weatherdemo.application.WeatherDemoApp
import com.weatherdemo.rxBus.RxHelper
import com.weatherdemo.utils.AppUtils
import com.weatherdemo.utils.PreferenceHelper
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun getLayoutId(): Int
    protected abstract fun getContainer(): Int
    protected abstract fun initViews()
    protected abstract fun handleBusCallback(event: Any)
    @Inject
    lateinit var rxBus: RxHelper
    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    private var showNetworkChanged: Boolean = false
    private var receiver: BroadcastReceiver? = null
    private var disposable: DisposableObserver<Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        WeatherDemoApp.getContext()?.getApplicationComponent()?.inject(this)
        addBackStackChangeListener()
        addNetworkChangeListener()
        registerForBusCallback()
        initViews()
    }

    private fun addBackStackChangeListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            val manager = supportFragmentManager
            if (manager != null) {
                val fragment = manager.findFragmentById(getContainer()) as BaseFragment?
                fragment?.resumeScreen()
            }
        }
    }

    private fun addNetworkChangeListener() {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                updateNetworkStatusChange(AppUtils.isNetworkConnected())
            }
        }
    }

    private fun updateNetworkStatusChange(isConnected: Boolean) {
        if (!isConnected) {
            showNetworkChanged = true
            showSnackBarMessage(getString(R.string.disconnected))
        } else {
            if (showNetworkChanged) {
                showSnackBarMessage(getString(R.string.connected))
            }
        }
    }

    private fun registerForBusCallback() {
        disposable = object : DisposableObserver<Any>() {
            override fun onNext(event: Any) {
                handleBusCallback(event)
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        }
        rxBus.toObservable()?.share()?.subscribeWith(disposable)
    }

    private fun unSubScribe() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
            disposable = null
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(
            receiver, IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION
            )
        )
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        unSubScribe()
        super.onDestroy()
    }

    private fun showSnackBarMessage(message: String) {
        val parentLayout = findViewById<View>(android.R.id.content)
        if (parentLayout != null) {
            val snackBar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
            snackBar.show()
        }
    }
}
