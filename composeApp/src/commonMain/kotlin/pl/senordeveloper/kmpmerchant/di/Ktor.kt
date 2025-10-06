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
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import pl.senordeveloper.kmpmerchant.network.dto.RefreshTokenRequest
import pl.senordeveloper.kmpmerchant.network.dto.Tokens
import pl.senordeveloper.kmpmerchant.network.services.AuthService
import pl.senordeveloper.kmpmerchant.network.services.UserService
import pl.senordeveloper.kmpmerchant.network.services.impl.AuthServiceImpl
import pl.senordeveloper.kmpmerchant.network.services.impl.UserServiceImpl
import pl.senordeveloper.kmpmerchant.viewmodel.LoginViewModel
import pl.senordeveloper.kmpmerchant.viewmodel.UserLoggedInViewModel
import pl.senordeveloper.kmpmerchant.viewmodel.UsersViewModel
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
                    val storage: Storage = get()
                    loadTokens {
                        storage.getBearerToken().also {
                            Log.debug("loadTokens $it")
                        }
                    }
                    refreshTokens {
                        oldTokens?.let { oldTokens ->
                            oldTokens.refreshToken?.let { refreshToken ->
                                client.post(urlString = "https://dummyjson.com/auth/refresh") {
                                    accept(ContentType.Application.Json)
                                    contentType(ContentType.Application.Json)
                                    setBody(
                                        RefreshTokenRequest(
                                            refreshToken = refreshToken,
                                        ).also {
                                            Log.debug("refreshTokens $it")
                                        }
                                    )
                                    markAsRefreshTokenRequest()
                                }.body<Tokens>().let { tokens ->
                                    storage.setTokens(
                                        accessToken = tokens.accessToken,
                                        refreshToken = tokens.refreshToken
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    single<AuthService> { AuthServiceImpl(get()) }
    single<UserService> { UserServiceImpl(get()) }
    single<Storage> { Storage() }
}

class Storage {
    var tokens: BearerTokens? = null
    fun getBearerToken(): BearerTokens? = tokens

    fun setTokens(accessToken: String, refreshToken: String?): BearerTokens {
        val bearerTokens = BearerTokens(accessToken, refreshToken)
        tokens = bearerTokens
        return bearerTokens
    }
}

val viewModelsModule = module {
    viewModel { LoginViewModel(authService = get(), storage = get()) }
    viewModel { UserLoggedInViewModel(authService = get()) }
    viewModel { UsersViewModel(userService = get()) }
}