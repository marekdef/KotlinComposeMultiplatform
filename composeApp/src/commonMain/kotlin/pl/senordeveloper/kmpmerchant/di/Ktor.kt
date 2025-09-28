package pl.senordeveloper.kmpmerchant.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation // Added import
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json // Added import
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import pl.senordeveloper.kmpmerchant.AppViewModel
import pl.senordeveloper.kmpmerchant.network.services.AuthService
import pl.senordeveloper.kmpmerchant.network.services.impl.AuthServiceImpl

val networkModule = module {
    single<HttpClient> { HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    } }

    single<AuthService> { AuthServiceImpl(get())}
}

val viewModelsModule = module {
    viewModel { AppViewModel(get()) }
}