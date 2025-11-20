package pl.senordeveloper.kmpmerchant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.senordeveloper.kmpmerchant.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(item: UserEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insert(items: List<UserEntity>)

    @Query("SELECT count(*) FROM users")
    suspend fun count(): Int

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<UserEntity>>
}

