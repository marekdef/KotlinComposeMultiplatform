package pl.senordeveloper.kmpmerchant.network.dto.users

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val department: String,
    val name: String,
    val title: String,
    val address: Address,
)