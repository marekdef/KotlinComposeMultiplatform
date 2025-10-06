package pl.senordeveloper.kmpmerchant.network.services

import arrow.core.Either
import pl.senordeveloper.kmpmerchant.network.dto.UserListResponse

interface UserService {
    suspend fun getUsers(): Either<Throwable, UserListResponse>
}