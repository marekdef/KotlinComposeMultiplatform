package pl.senordeveloper.kmpmerchant

import android.content.Context
import androidx.room.RoomDatabase
import okio.Path.Companion.toOkioPath
import org.koin.dsl.module
import pl.senordeveloper.kmpmerchant.database.AppDatabase
import pl.senordeveloper.kmpmerchant.database.getDatabaseBuilder
import pl.senordeveloper.kmpmerchant.datastore.PathProvider

val androidModule = module {
    single<PathProvider> {
        val context = get<Context>()
        return@single { fileName: String ->
            context.filesDir.resolve(fileName).absoluteFile.toOkioPath()
        }
    }

    single<RoomDatabase.Builder<AppDatabase>> {
        getDatabaseBuilder(get())
    }
}