package pl.senordeveloper.kmpmerchant.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import pl.senordeveloper.kmpmerchant.database.databaseModule

fun koinModules() = listOf(networkModule, viewModelsModule, dataStoreModule, databaseModule)

fun initKoin(appDeclaration: KoinAppDeclaration = {}){
    startKoin {
        appDeclaration()
        modules(koinModules() + platformModules())
    }
}