package pl.senordeveloper.kmpmerchant.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Tokens(
    val accessToken: String,
    val refreshToken: String,
)