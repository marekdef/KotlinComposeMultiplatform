package pl.senordeveloper.kmpmerchant.network.services.impl

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import pl.senordeveloper.kmpmerchant.di.LoginRequest
import pl.senordeveloper.kmpmerchant.di.Tokens
import pl.senordeveloper.kmpmerchant.di.User
import pl.senordeveloper.kmpmerchant.di.UserWithTokens
import pl.senordeveloper.kmpmerchant.network.services.AuthService

class AuthServiceImpl(
    val httpClient: HttpClient
) : AuthService {
    override suspend fun login(
        username: String,
        password: String
    ): Either<Throwable, UserWithTokens> = Either.catch {
        httpClient.post(
            urlString = "https://dummyjson.com/auth/login"
        ) {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(username, password))
        }.body()
    }

    override suspend fun get(): Either<Throwable, User> = Either.catch {
        httpClient.get(
            urlString = "https://dummyjson.com/auth/me"
        ) {
            accept(ContentType.Application.Json)
        }.body<User>()
    }

    override suspend fun refreshTokens(): Either<Exception, Tokens> {
        TODO("Not yet implemented")
    }

}