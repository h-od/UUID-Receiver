package com.hod.uuidreceiver

import android.app.Application
import com.hod.uuidreceiver.di.*

class App : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .presenterModule(PresenterModule())
                .dataModule(DataModule())
                .build()
    }
}
