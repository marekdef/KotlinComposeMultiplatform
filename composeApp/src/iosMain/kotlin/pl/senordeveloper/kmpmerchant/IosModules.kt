package pl.senordeveloper.kmpmerchant

import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.dsl.module
import pl.senordeveloper.kmpmerchant.database.AppDatabase
import pl.senordeveloper.kmpmerchant.database.getDatabaseBuilder
import pl.senordeveloper.kmpmerchant.datastore.PathProvider
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
val iosModule = module {
    single<PathProvider> {
        { fileName: String ->
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory)
            (documentDirectory.path + "/$fileName").toPath()
        }
    }

    single<RoomDatabase.Builder<AppDatabase>> {
        getDatabaseBuilder()
    }
}