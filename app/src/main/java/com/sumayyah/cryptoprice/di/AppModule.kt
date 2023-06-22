package com.sumayyah.cryptoprice.di

import android.app.Application
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.anvil.annotations.ContributesTo
import com.sumayyah.cryptoprice.DefaultConfiguration
import com.sumayyah.cryptoprice.data.CoinDao
import com.sumayyah.cryptoprice.network.CoinApi
import com.sumayyah.cryptoprice.ui.main.MainViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@ContributesTo(AppScope::class)
class AppModule() {

    @SingleIn(AppScope::class)
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

        val paramInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                val url = request.url.newBuilder()
                    .addQueryParameter("fiat", "btc")
                    .addQueryParameter("key", DefaultConfiguration.apiKey)
                    .build()
                request = request.newBuilder().url(url).build()
                return chain.proceed(request)
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(paramInterceptor)
            .cookieJar(JavaNetCookieJar(cookieManager))
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build()
    }

    @SingleIn(AppScope::class)
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(DefaultConfiguration.baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @SingleIn(AppScope::class)
    @Provides
    fun provideCoinApi(retrofit: Retrofit): CoinApi {
        return retrofit.create(CoinApi::class.java)
    }

    @SingleIn(AppScope::class)
    @Provides
    fun provideMainViewModelFactory(coinApi: CoinApi, coinDao: CoinDao): MainViewModelFactory {
        return MainViewModelFactory(coinApi, coinDao)
    }

    @SingleIn(AppScope::class)
    @Provides
    fun provideCoinDao(): CoinDao {
        return CoinDao()
    }
}