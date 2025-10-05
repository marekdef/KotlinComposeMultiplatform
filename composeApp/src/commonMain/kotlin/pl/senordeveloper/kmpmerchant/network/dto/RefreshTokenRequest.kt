package pl.senordeveloper.kmpmerchant.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val refreshToken: String,
    val expiresInMins: Int? = null
)