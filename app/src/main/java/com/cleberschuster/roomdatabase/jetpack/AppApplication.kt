package com.cleberschuster.roomdatabase.jetpack

import android.app.Application
import com.cleberschuster.roomdatabase.jetpack.di.daoModule
import com.cleberschuster.roomdatabase.jetpack.di.repositoryModule
import com.cleberschuster.roomdatabase.jetpack.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            modules(viewModelModule, daoModule, repositoryModule)

        }
    }
}