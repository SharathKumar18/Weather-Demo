package com.weatherdemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.observers.DisposableObserver

abstract class BaseFragment : Fragment() {

    protected abstract fun getFragmentLayoutId(): Int
    protected abstract fun initViews(view: View)
    abstract fun resumeScreen()
    protected abstract fun handleBusCallback(event: Any)
    private var disposable: DisposableObserver<Any>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getFragmentLayoutId(), container, false)
        registerForBusCallback()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun registerForBusCallback() {
        disposable = object : DisposableObserver<Any>() {
            override fun onNext(event: Any) {
                handleBusCallback(event)
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        }
    }

    private fun unSubScribe() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
            disposable = null
        }
    }

    override fun onDestroyView() {
        unSubScribe()
        super.onDestroyView()
    }
}