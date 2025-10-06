package pl.senordeveloper.kmpmerchant.network.services.impl

import arrow.core.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import pl.senordeveloper.kmpmerchant.network.dto.UserListResponse
import pl.senordeveloper.kmpmerchant.network.services.UserService

class UserServiceImpl(
    val httpClient: HttpClient
): UserService {
    override suspend fun getUsers(): Either<Throwable, UserListResponse>  = Either.catch {
        httpClient.get("https://dummyjson.com/users") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }.body<UserListResponse>()
    }
}