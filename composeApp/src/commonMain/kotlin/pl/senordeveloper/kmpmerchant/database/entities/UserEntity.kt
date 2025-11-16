package pl.senordeveloper.kmpmerchant.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0)