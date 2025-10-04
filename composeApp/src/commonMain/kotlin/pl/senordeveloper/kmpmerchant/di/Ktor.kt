package pl.senordeveloper.kmpmerchant.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import pl.senordeveloper.kmpmerchant.network.dto.RefreshTokenRequest
import pl.senordeveloper.kmpmerchant.network.dto.Tokens
import pl.senordeveloper.kmpmerchant.network.services.AuthService
import pl.senordeveloper.kmpmerchant.network.services.impl.AuthServiceImpl
import pl.senordeveloper.kmpmerchant.viewmodel.LoginViewModel
import pl.senordeveloper.kmpmerchant.viewmodel.UserLoggedInViewModel
import saschpe.log4k.Log

val networkModule = module {
    single<HttpClient> {
        HttpClient() {
            expectSuccess = true

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
                    loadTokens {
                        val storage: Storage = get()
                        storage.getBearerToken().also {
                            Log.debug("loadTokens $it")
                        }
                    }
                    refreshTokens {
                        client.post(urlString = "https://dummyjson.com/auth/refresh") {
                            accept(ContentType.Application.Json)
                            contentType(ContentType.Application.Json)
                            setBody(
                                RefreshTokenRequest(
                                    refreshToken = oldTokens?.refreshToken,
                                    accessToken = oldTokens?.accessToken
                                ).also {
                                    Log.debug("refreshTokens $it")
                                }
                            )
                            markAsRefreshTokenRequest()
                        }.body<Tokens>().let {
                            val storage: Storage = get()
                            storage.setTokens(it.accessToken, it.refreshToken)
                            BearerTokens(it.accessToken, it.refreshToken)
                        }
                    }
                }
            }
        }
    }

    single<AuthService> { AuthServiceImpl(get()) }
    single<Storage> { Storage() }
}

class Storage {
    var tokens: BearerTokens? = null
    fun getBearerToken(): BearerTokens? = tokens

    fun setTokens(accessToken: String, refreshToken: String?) {
        tokens = BearerTokens(accessToken, refreshToken)
    }
}

val viewModelsModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { UserLoggedInViewModel(get()) }
}