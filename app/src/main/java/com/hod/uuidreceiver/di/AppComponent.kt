package com.hod.uuidreceiver.di

import android.app.Application
import android.content.Context
import com.hod.uuidreceiver.data.DataContract
import com.hod.uuidreceiver.ui.MainActivity
import com.hod.uuidreceiver.ui.MainPresenter
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PresenterModule::class, DataModule::class])
interface AppComponent {

    fun inject(target: MainActivity)

}

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app
}

@Module
class PresenterModule {
    @Provides
    @Singleton
    fun providePresenter(dataManager: DataContract): MainPresenter =
            MainPresenter(dataManager, AndroidSchedulers.mainThread())
}
