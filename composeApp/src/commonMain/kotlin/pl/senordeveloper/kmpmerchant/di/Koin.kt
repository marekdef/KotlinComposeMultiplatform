package pl.senordeveloper.kmpmerchant.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun koinModules() = listOf(networkModule, viewModelsModule, prefsModule)

fun initKoin(appDeclaration: KoinAppDeclaration = {}){
    startKoin {
        appDeclaration()
        modules(koinModules() + platformModules())
    }
}