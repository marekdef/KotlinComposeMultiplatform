package pl.senordeveloper.kmpmerchant.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val expiresInMins: Int? = null
)