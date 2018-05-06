package com.hod.uuidreceiver

import android.app.Application
import com.hod.uuidreceiver.di.*

class App : Application() {
    val component: AppComponent by lazy {
        val networkModule = NetworkModule.Impl()
        val localStorageModule = LocalStorageModule.Impl(this)
        val dataModule= DataModule.Impl(localStorageModule.local, networkModule.network)
        val presenterModule = PresenterModule.Impl(dataModule.dataManager)

        AppComponent(
                presenterModule,
                dataModule,
                localStorageModule,
                networkModule)
    }
}
