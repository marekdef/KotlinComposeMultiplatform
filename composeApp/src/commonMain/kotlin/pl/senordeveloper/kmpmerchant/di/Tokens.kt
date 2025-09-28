package pl.senordeveloper.kmpmerchant.di

import kotlinx.serialization.Serializable

@Serializable
data class Tokens(
    val accessToken: String,
    val refreshToken: String,
)