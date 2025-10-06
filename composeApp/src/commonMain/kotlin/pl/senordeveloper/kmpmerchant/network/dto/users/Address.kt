package pl.senordeveloper.kmpmerchant.network.dto.users

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val address: String,
    val city: String,
    val state: String,
    val stateCode: String,
    val postalCode: String,
    val coordinates: Coordinates,
    val country: String,
)