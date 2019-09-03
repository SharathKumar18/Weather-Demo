package com.weatherdemo.dagger.component

import com.weatherdemo.base.BaseActivity
import com.weatherdemo.base.BaseFragment
import com.weatherdemo.base.BaseViewHolder
import com.weatherdemo.base.BaseViewModel
import com.weatherdemo.dagger.module.AppModule
import com.weatherdemo.dagger.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {

    fun inject(baseActivity: BaseActivity)

    fun inject(baseFragment: BaseFragment)

    fun inject(baseViewModel: BaseViewModel)

    fun inject(viewHolder: BaseViewHolder)
}
