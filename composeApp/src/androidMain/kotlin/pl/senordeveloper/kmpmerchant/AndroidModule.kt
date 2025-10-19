package pl.senordeveloper.kmpmerchant

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toOkioPath
import org.koin.dsl.module

val androidModule = module {
    single<DataStore<Preferences>> {
        val context = get<Context>()
        PreferenceDataStoreFactory.createWithPath {
            context.filesDir.resolve("dummyjson.preferences_pb").absoluteFile.toOkioPath()
        }
    }
}