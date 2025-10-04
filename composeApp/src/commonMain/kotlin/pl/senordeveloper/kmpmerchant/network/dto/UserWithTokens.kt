package pl.senordeveloper.kmpmerchant.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserWithTokens(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val accessToken: String,
    val refreshToken: String,
) {
}
