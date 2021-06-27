package com.cleberschuster.roomdatabase.jetpack.di

import com.cleberschuster.roomdatabase.jetpack.data.db.AppDatabase
import com.cleberschuster.roomdatabase.jetpack.repository.DatabaseDataSource
import com.cleberschuster.roomdatabase.jetpack.repository.SubscriberRepository
import com.cleberschuster.roomdatabase.jetpack.ui.subiscriberlist.SubscriberListViewModel
import com.cleberschuster.roomdatabase.jetpack.ui.subscriber.SubscriberViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SubscriberViewModel(get()) }
    viewModel { SubscriberListViewModel(get()) }
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()).subscriberDAO() }
}

val repositoryModule = module {
    single<SubscriberRepository> { DatabaseDataSource(get()) }
}
