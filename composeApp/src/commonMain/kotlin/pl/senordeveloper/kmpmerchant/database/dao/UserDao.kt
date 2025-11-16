package pl.senordeveloper.kmpmerchant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.senordeveloper.kmpmerchant.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(item: UserEntity)

    @Query("SELECT count(*) FROM UserEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM UserEntity")
    fun getAll(): Flow<List<UserEntity>>
}
