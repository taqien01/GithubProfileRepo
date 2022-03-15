package id.reza.profilegithub.di

import id.reza.profilegithub.service.AppService
import id.reza.profilegithub.service.NetworkConnectionInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single { NetworkConnectionInterceptor(get()) }

    single(named(appService)) { AppService.getServices(get()) }
}

const val appService = "APP_SERVICE"