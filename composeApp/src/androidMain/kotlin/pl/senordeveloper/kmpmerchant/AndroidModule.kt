package pl.senordeveloper.kmpmerchant

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toOkioPath
import org.koin.dsl.module
import pl.senordeveloper.kmpmerchant.datastore.PathProvider

val androidModule = module {
    single<PathProvider> {
        val context = get<Context>()
        return@single { fileName: String ->
            context.filesDir.resolve(fileName).absoluteFile.toOkioPath()
        }
    }
}