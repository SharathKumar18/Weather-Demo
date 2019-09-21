package com.weatherdemo.dagger.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.weatherdemo.network.DataManager
import com.weatherdemo.network.RetrofitInterface
import com.weatherdemo.utils.ApiConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    val okHttpClient: OkHttpClient
        @Provides
        @Singleton
        get() {
            try {
                val httpClient = OkHttpClient.Builder()
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                httpClient.addInterceptor(interceptor)
                httpClient.addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build()
                    chain.proceed(request)
                }
                return httpClient.build()
            } catch (e: Exception) {
                return OkHttpClient().newBuilder().build()
            }

        }

    internal val gson: Gson
        @Provides
        @Singleton
        get() = GsonBuilder()
            .setLenient()
            .create()

    val retrofit: Retrofit
        @Provides
        @Singleton
        get() = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    val retrofitInterface: RetrofitInterface
        @Provides
        @Singleton
        get() = retrofit.create(RetrofitInterface::class.java)

    @Provides
    @Singleton
    internal fun provideDataManagerClass(): DataManager {
        return DataManager(retrofitInterface)
    }
}
