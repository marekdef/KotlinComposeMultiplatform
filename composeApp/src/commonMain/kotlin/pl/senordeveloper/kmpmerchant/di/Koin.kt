package pl.senordeveloper.kmpmerchant.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun koinModules() = listOf(networkModule, viewModelsModule, dataStoreModule)

fun initKoin(appDeclaration: KoinAppDeclaration = {}){
    startKoin {
        appDeclaration()
        modules(koinModules() + platformModules())
    }
}