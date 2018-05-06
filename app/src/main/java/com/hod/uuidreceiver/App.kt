package com.hod.uuidreceiver

import android.app.Application
import android.content.Context
import com.hod.uuidreceiver.di.DataModule

class App : Application() { val component: AppComponent by lazy { AppComponent(this) } }

class AppComponent(context: Context) : DataModule by DataModule.Impl(context)