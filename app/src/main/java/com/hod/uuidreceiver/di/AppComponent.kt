package com.hod.uuidreceiver.di

class AppComponent(
        presenterModule: PresenterModule,
        dataModule: DataModule,
        localStorageModule: LocalStorageModule,
        networkModule: NetworkModule
) : Component,
        PresenterModule by presenterModule,
        DataModule by dataModule,
        LocalStorageModule by localStorageModule,
        NetworkModule by networkModule

