package com.weatherdemo.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.weatherdemo.R
import com.weatherdemo.base.BaseActivity
import com.weatherdemo.rxBus.RxEvent
import com.weatherdemo.rxBus.RxEvent.Companion.EVENT_LOAD_HOME
import com.weatherdemo.ui.splash.SplashFragment
import com.weatherdemo.utils.AppConstants
import com.weatherdemo.utils.AppConstants.KEY_CITY_NAME
import com.weatherdemo.utils.FragmentNavigator
import com.weatherdemo.utils.LocationHelperUtil
import com.weatherdemo.utils.LocationHelperUtil.checkIfLocationIsEnabled

class HomeActivity : BaseActivity(),LocationListener {

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun getContainer(): Int {
        return R.id.homeContainer
    }

    override fun onStart() {
        super.onStart()
        val isPermissionGranted = LocationHelperUtil.checkLocationPermission(this)
        if (isPermissionGranted && checkIfLocationIsEnabled()) {
            getUserLocation()
        }
    }

    override fun initViews() {
        loadSplashFragment()
    }

    private fun getUserLocation() {
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
            LOCATION_REFRESH_DISTANCE.toFloat(), this
        )
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            LOCATION_REFRESH_TIME,
            LOCATION_REFRESH_DISTANCE.toFloat(), this
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
                        requestLocation()
                    }
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOCATION_SETTINGS_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                getUserLocation()
            }
        }
    }

    private fun requestLocation() {
        val mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(10 * 1000)
            .setFastestInterval(1000)

        val settingsBuilder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest)
        settingsBuilder.setAlwaysShow(true)

        val settingsClient = LocationServices.getSettingsClient(this)
        val result = settingsClient.checkLocationSettings(settingsBuilder.build())
        result.addOnSuccessListener { getUserLocation() }
            .addOnFailureListener { e ->
                val statusCode = (e as ApiException).statusCode
                if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    try {
                        val exception = e as ResolvableApiException
                        exception.startResolutionForResult(
                            this@HomeActivity,
                            LOCATION_SETTINGS_REQUEST
                        )
                    } catch (e: IntentSender.SendIntentException) {
                    }
                }
            }
    }

    override fun onLocationChanged(location: Location) {
        saveLocationData(location)
        val event = RxEvent(RxEvent.EVENT_LOCATION_UPDATED, location)
        rxBus.send(event)
    }

    private fun saveLocationData(location: Location) {
        val cityName = LocationHelperUtil.getCity(
            this@HomeActivity,
            location.latitude,
            location.longitude
        )
        if (cityName != null) {
            preferenceHelper.editPrefString(KEY_CITY_NAME, cityName)
        }
        preferenceHelper.editPrefLong(AppConstants.KEY_LATITUDE, location.latitude.toFloat())
        preferenceHelper.editPrefLong(AppConstants.KEY_LONGITUDE, location.longitude.toFloat())
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    override fun onStop() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(this)
        super.onStop()
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
        private const val LOCATION_SETTINGS_REQUEST=1
    }
}