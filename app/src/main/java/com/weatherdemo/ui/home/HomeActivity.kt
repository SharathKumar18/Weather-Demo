package com.weatherdemo.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.weatherdemo.R
import com.weatherdemo.base.BaseActivity
import com.weatherdemo.rxBus.RxEvent
import com.weatherdemo.rxBus.RxEvent.Companion.EVENT_LOAD_HOME
import com.weatherdemo.ui.splash.SplashFragment
import com.weatherdemo.utils.AppConstants
import com.weatherdemo.utils.AppConstants.KEY_CITY_NAME
import com.weatherdemo.utils.FragmentNavigator
import com.weatherdemo.utils.LocationHelperUtil
import com.weatherdemo.utils.Logger

class HomeActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun getContainer(): Int {
        return R.id.homeContainer
    }

    override fun initViews() {
        loadSplashFragment()
        val isPermissionGranted = LocationHelperUtil.checkLocationPermission(this)
        if (isPermissionGranted) {
            findUserLocation()
        }
    }

    private fun findUserLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            LOCATION_REFRESH_TIME,
            LOCATION_REFRESH_DISTANCE.toFloat(), locationListener
        )
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            LOCATION_REFRESH_TIME,
            LOCATION_REFRESH_DISTANCE.toFloat(), locationListener
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            AppConstants.GET_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (LocationHelperUtil.checkSelfPermission(this)) {
                        findUserLocation()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(locationListener)
        super.onDestroy()
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val cityName = LocationHelperUtil.getCity(
                this@HomeActivity,
                location.latitude,
                location.longitude
            )
            Logger.i("WeatherData", ""+cityName + location.latitude + location.longitude)
            if (cityName != null) {
                preferenceHelper.editPrefString(KEY_CITY_NAME, cityName)
            }
            preferenceHelper.editPrefLong(AppConstants.KEY_LATITUDE, location.latitude.toFloat())
            preferenceHelper.editPrefLong(AppConstants.KEY_LONGITUDE, location.longitude.toFloat())
            val event = RxEvent(RxEvent.EVENT_LOCATION_UPDATED, location)
            rxBus.send(event)
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

        }

        override fun onProviderEnabled(s: String) {

        }

        override fun onProviderDisabled(s: String) {

        }
    }

    override fun handleBusCallback(event: Any) {
        val rxEvent = event as RxEvent<*>
        when (rxEvent.eventTag) {
            EVENT_LOAD_HOME -> {
                loadHomeFragment()
            }
        }
    }

    private fun loadSplashFragment() {
        FragmentNavigator.replaceFragment(
            this, supportFragmentManager,
            getContainer(), SplashFragment.newInstance(), null, false,
            SplashFragment::class.java.simpleName
        )
    }

    private fun loadHomeFragment() {
        FragmentNavigator.replaceFragment(
            this, supportFragmentManager,
            getContainer(), HomeFragment.newInstance(), null, false,
            HomeFragment::class.java.simpleName
        )
    }

    companion object {
        private const val LOCATION_REFRESH_TIME: Long = 1000
        private const val LOCATION_REFRESH_DISTANCE: Long = 1000
    }
}