package pl.senordeveloper.kmpmerchant.network.dto.users

import kotlinx.serialization.Serializable

@Serializable
data class Crypto(
    val coin: String,
    val wallet: String,
    val network: String,
)