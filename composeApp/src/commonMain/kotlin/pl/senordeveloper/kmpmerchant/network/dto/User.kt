package pl.senordeveloper.kmpmerchant.network.dto

import kotlinx.serialization.Serializable
import pl.senordeveloper.kmpmerchant.database.entities.UserEntity
import pl.senordeveloper.kmpmerchant.network.dto.users.FullUser

@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
) {
    companion object {
        fun fromFullUser(fullUser: FullUser): User = with(fullUser) {
            User(
                id = id.toString(),
                username = username,
                email = email,
                firstName = firstName,
                lastName = lastName,
                image = image,
                gender = gender
            )
        }

        fun fromEntity(entity: UserEntity): User = with(entity) {
            User(
                id = id.toString(),
                username = username,
                email = email,
                firstName = firstName,
                lastName = lastName,
                image = image,
                gender = gender
            )
        }
    }
}

