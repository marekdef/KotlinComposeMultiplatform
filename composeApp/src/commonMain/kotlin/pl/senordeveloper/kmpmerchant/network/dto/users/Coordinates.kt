package pl.senordeveloper.kmpmerchant.network.dto.users

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val lat: Double,
    val lng: Double,
)