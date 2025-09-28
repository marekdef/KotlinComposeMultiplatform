package pl.senordeveloper.kmpmerchant.network.services

import arrow.core.Either
import pl.senordeveloper.kmpmerchant.di.Tokens
import pl.senordeveloper.kmpmerchant.di.User
import pl.senordeveloper.kmpmerchant.di.UserWithTokens

interface AuthService {
    suspend fun login(username: String, password: String): Either<Exception, UserWithTokens>
    suspend fun get(): Either<Exception, User>
    suspend fun refreshTokens(): Either<Exception, Tokens>
}