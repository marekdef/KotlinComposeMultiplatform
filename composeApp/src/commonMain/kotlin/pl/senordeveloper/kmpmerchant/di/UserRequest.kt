package pl.senordeveloper.kmpmerchant.di

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val username: String,
    val password: String
) {

}
