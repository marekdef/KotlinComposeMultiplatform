package pl.senordeveloper.kmpmerchant.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import pl.senordeveloper.kmpmerchant.database.dao.UserDao
import pl.senordeveloper.kmpmerchant.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ], version = 2,
    exportSchema = true,

)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}