package pl.senordeveloper.kmpmerchant.network.dto

import kotlinx.serialization.Serializable
import pl.senordeveloper.kmpmerchant.network.dto.users.FullUser

@Serializable
data class UserListResponse(
    val users: List<FullUser>,
    val total: Int,
    val skip: Int,
    val limit: Int
)