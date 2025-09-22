package pl.senordeveloper.kmpmerchant.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody // <- Add this import
import io.ktor.http.ContentType       // <- Add this import
import io.ktor.http.contentType     // <- Add this import

class ApiServiceImpl(val httpClient: HttpClient) : ApiService {
    override suspend fun getText(text: String): String = httpClient.get(
        urlString = "https://httpbin.org/base64/$text"
    ).body()

    override suspend fun login(user: UserRequest): User = httpClient.post(
        urlString = "https://dummyjson.com/auth/login"
    ) {
        contentType(ContentType.Application.Json) // Set the content type to application/json
        setBody(user)                             // Set the 'user' object as the request body
    }
        .body()
}
