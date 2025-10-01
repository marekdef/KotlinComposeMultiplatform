package pl.senordeveloper.kmpmerchant.di

import androidx.compose.material3.Button
import androidx.compose.ui.graphics.drawscope.Stroke
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation // Added import
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.channels.Channel
import kotlinx.serialization.json.Json // Added import
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import pl.senordeveloper.kmpmerchant.network.services.AuthService
import pl.senordeveloper.kmpmerchant.network.services.impl.AuthServiceImpl
import pl.senordeveloper.kmpmerchant.viewmodel.LoginViewModel
import pl.senordeveloper.kmpmerchant.viewmodel.UserLoggedInViewModel

val networkModule = module {
    single<HttpClient> {
        HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = Logger.SIMPLE // Uses a simple logger that prints to stdout
                level = LogLevel.ALL    // Log all levels (HEADERS, BODY, INFO)
            }

            install(Auth) {
                bearer {
                    sendWithoutRequest { request ->
                        request.url.encodedPath.contains("/auth/login")
                    }
                    loadTokens {
                        val storage: Storage = get()
                        storage.getBearerToken()
                    }
                }

            }

            expectSuccess = true
        }
    }

    single<AuthService> { AuthServiceImpl(get()) }
    single<Storage> { Storage() }
}

class Storage {
    var tokens: BearerTokens? = null
    fun getBearerToken(): BearerTokens?  = tokens

    fun setTokens(accessToken: String, refreshToken: String?) {
        tokens = BearerTokens(accessToken, refreshToken)
    }
}

val viewModelsModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { UserLoggedInViewModel(get()) }
}