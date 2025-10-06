package pl.senordeveloper.kmpmerchant.network.dto.users

import kotlinx.serialization.Serializable

@Serializable
data class Hair(
    val color: String,
    val type: String,
)