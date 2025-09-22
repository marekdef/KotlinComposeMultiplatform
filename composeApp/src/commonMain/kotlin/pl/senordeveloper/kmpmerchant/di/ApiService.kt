package pl.senordeveloper.kmpmerchant.di

import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope

interface ApiService {
    suspend fun getText(text: String): String

    suspend fun login(user: UserRequest): User
}
