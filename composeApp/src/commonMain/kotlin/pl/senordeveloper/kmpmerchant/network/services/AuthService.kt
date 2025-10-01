package pl.senordeveloper.kmpmerchant.network.services

import arrow.core.Either
import pl.senordeveloper.kmpmerchant.di.Tokens
import pl.senordeveloper.kmpmerchant.di.User
import pl.senordeveloper.kmpmerchant.di.UserWithTokens

interface AuthService {
    suspend fun login(username: String, password: String): Either<Throwable, UserWithTokens>
    suspend fun get(): Either<Throwable, User>
    suspend fun refreshTokens(): Either<Throwable, Tokens>
}