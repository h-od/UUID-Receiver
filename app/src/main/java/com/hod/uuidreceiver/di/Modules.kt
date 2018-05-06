package com.hod.uuidreceiver.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.hod.uuidreceiver.BuildConfig.BASE_URL
import com.hod.uuidreceiver.data.*
import com.hod.uuidreceiver.ui.MainPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface Component: PresenterModule, DataModule, LocalStorageModule, NetworkModule

interface PresenterModule {

    val presenter: MainPresenter

    class Impl(dataContract: DataContract) : PresenterModule {
        override val presenter: MainPresenter by lazy { MainPresenter(dataContract, AndroidSchedulers.mainThread(), Schedulers.io()) }
    }
}

interface DataModule {

    val dataManager: DataContract

    class Impl(local: Local, network: Remote) : DataModule {
        override val dataManager: DataContract by lazy { DataManager(local, network) }
    }
}

interface LocalStorageModule {

    val local: Local
    val sharedPreferences: SharedPreferences

    class Impl(context: Context) : LocalStorageModule {
        override val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        override val local: Local = LocalStorage(sharedPreferences)
    }
}

interface NetworkModule {
    val network: Remote

    val retrofit: Retrofit
    val httpClient: OkHttpClient
    val loggingInterceptor: HttpLoggingInterceptor

    class Impl() : NetworkModule {
        override val loggingInterceptor: HttpLoggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        override val httpClient: OkHttpClient =
                OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .hostnameVerifier { _, _ -> true }
                        .build()

        override val retrofit: Retrofit =
                Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient)
                        .build()

        override val network: Remote = retrofit.create<Remote>(Remote::class.java)
    }
}
