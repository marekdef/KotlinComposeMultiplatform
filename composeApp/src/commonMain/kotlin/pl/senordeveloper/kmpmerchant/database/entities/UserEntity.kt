package pl.senordeveloper.kmpmerchant.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.senordeveloper.kmpmerchant.network.dto.users.FullUser


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "image")
    val image: String,
) {
    companion object {
        fun from(fullUser: FullUser): UserEntity =
            with(fullUser)  {
                UserEntity(
                    id = fullUser.id,
                    firstName = firstName,
                    lastName = lastName,
                    gender = gender,
                    image = image,
                    username = username,
                    email = email
                )
            }
    }
}
