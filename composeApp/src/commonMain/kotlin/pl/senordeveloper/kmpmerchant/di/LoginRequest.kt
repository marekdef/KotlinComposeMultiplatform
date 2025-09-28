package pl.senordeveloper.kmpmerchant.di

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
) {

}
