package pl.senordeveloper.kmpmerchant.di

import org.koin.core.context.startKoin

fun koinModules() = listOf(networkModule, viewModelsModule)

fun initKoin(){
    startKoin {
        modules(koinModules())
    }
}