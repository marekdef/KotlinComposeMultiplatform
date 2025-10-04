@file:OptIn(ExperimentalSerializationApi::class)

package pl.senordeveloper.kmpmerchant.network.dto

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String,
    @EncodeDefault val expiresInMins: Int = 1
) {

}
